package com.ef;

import com.ef.configuration.Duration;
import com.ef.domain.AccessLog;
import com.ef.domain.BlockedIp;
import com.ef.repository.BlockedIpRepository;
import com.ef.repository.LoggerRepository;
import com.ef.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(ParserApplication.class);
    private final LoggerService loggerService;
    private final LoggerRepository loggerRepository;
    private final BlockedIpRepository blockedIpRepository;

    @Autowired
    public ParserApplication(LoggerService loggerService, LoggerRepository loggerRepository, BlockedIpRepository blockedIpRepository) {
        this.loggerService = loggerService;
        this.loggerRepository = loggerRepository;
        this.blockedIpRepository = blockedIpRepository;
    }

    public static void main(String[] args) {
        LOGGER.info("Starting Parser Application");
        SpringApplication.run(ParserApplication.class, args);
        LOGGER.info("Parser Application Finished");
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Executing run method with args {}:", args);
        if (args.length == 4) {
            // Take program arguments from console
            String fileName = args[0]; // access log file path
            LocalDateTime startDate = LoggerService.parseStringToLocalDateTime(args[1]);
            String duration = args[2]; // duration
            LocalDateTime endDate = duration.equals(Duration.HOURLY.name()) ? startDate.plusHours(1L) : startDate.plusHours(24L);
            Long threshold = Long.parseLong(args[3]);

            // Parse log file(with format: Date|IP|Request|Status|User Agent) to Access log object
            List<AccessLog> accessLogs = loggerService.parseFileToAccessLogObject(fileName);

            if (loggerRepository.findAll().size() > 0) {
                // Do nothing. workaround for avoiding duplication inserts,
                // if you are running program more then once without cleaning database
            } else {
                // Save all access logs to the database if not empty accessLogs
                loggerRepository.saveAll(accessLogs);
            }

            // Call logger repository proper query which will
            // The tool will find any IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00 (one hour) and print them to console AND also load them to another MySQL table with comments on why it's blocked.
            // Query based on coming parameters

            // Find all IP Address made more then threshold requests  192.168.11.231
            List<String> ips = loggerRepository.findIpAddresses(startDate, endDate, threshold);
            LOGGER.info("{} ips made more then {} requests, starting from {} to {}: ", ips, threshold, startDate, endDate);
            // Find all requests by Ip Address
            // foreach allIps println requests
            List<String> allRequests = loggerRepository.findRequestsByIpAddress("ip_address");

            LOGGER.info("{} ip made more then {} requests, starting from {} to {}: ", "ip_address",
                    threshold, startDate, endDate);
            // Save in blocked ip table
            blockedIpRepository.save(new BlockedIp("ip_address", "This ip address made to many requests"));
        } else {
            LOGGER.error("Please check that you are passing all arguments: accesslog,  startDate, duration, threshold.");
        }
    }
}