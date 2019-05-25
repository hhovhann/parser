package com.ef;

import com.ef.service.BlockedIpService;
import com.ef.service.LoggerService;
import com.ef.service.TransformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@SpringBootApplication
public class ParserApplication implements ApplicationRunner {
    @Value("${accessLog}")
    private String accessLogArg;
    @Value("${startDate}")
    private String startDateArg;
    @Value("${duration}")
    private String durationArg;
    @Value("${threshold}")
    private String thresholdArg;

    private static Logger LOGGER = LoggerFactory.getLogger(ParserApplication.class);
    private final LoggerService loggerService;
    private final BlockedIpService blockedIpService;


    @Autowired
    public ParserApplication(LoggerService loggerService, BlockedIpService blockedIpService) {
        this.loggerService = loggerService;
        this.blockedIpService = blockedIpService;
    }

    public static void main(String[] args) {
        LOGGER.info("Starting Parser Application");
        SpringApplication.run(ParserApplication.class, args);
        LOGGER.info("Parser Application Finished");
    }

    @Override
    public void run(ApplicationArguments args) {
        final long startTime = System.currentTimeMillis();
        LOGGER.info("Application started with command line arguments: {}", Arrays.toString(args.getSourceArgs()));
        if (args.getOptionNames().size() == 4) {
            // 0. Taking arguments from command line
            LocalDateTime startDate = TransformationService.parseToLocalDateTime(startDateArg);          // start date
            LocalDateTime endDate = TransformationService.retrieveEndDateTime(startDate, durationArg);  // end date
            Long threshold = Long.parseLong(thresholdArg);                                             // threshold
            // 1. Load all data from log file into database
            if (Objects.nonNull(accessLogArg) && !accessLogArg.trim().isEmpty()) {
                loggerService.loadAllDataToDatabase(accessLogArg);
                // 2. Retrieves all ip addresses filtered by predefined arguments
                List<String> ipAddresses = loggerService.findIpAddressesByArguments(startDate, endDate, threshold);
                // 3. Print all ip addresses filtered by predefined arguments to console
                LOGGER.info("{} ipAddresses made more then {} requests, starting from {} to {}: ", ipAddresses, threshold, startDate, endDate);
                // 4. Load all ip addresses to another MySQL table with comments on why it's blocked.
                if (ipAddresses.size() > 0) {
                    blockedIpService.loadAllIpAddressesToDatabaseWithComment(ipAddresses);
                    LOGGER.info("Following ip addresses was found and stored into blocked ip table {}: ", ipAddresses);
                }
            } else {
                LOGGER.error("Access log file empty or you are using wrong file. Please check");
            }
        } else {
            LOGGER.error("Seems one of the arguments you forgot." +
                    "Please check that you are passing all arguments: accessLog, startDate, duration, threshold.");
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("Application duration takes: {} milliseconds.", endTime - startTime);
    }
}