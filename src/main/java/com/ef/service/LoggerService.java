package com.ef.service;

import com.ef.domain.AccessLog;
import com.ef.repository.LoggerRepository;
import lombok.Lombok;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class LoggerService {
    private static Logger LOGGER = LoggerFactory.getLogger(LoggerService.class);
    private static final String PIPE = "\\|";
    private final Predicate<String> linePredicate = line -> !line.isEmpty();
    private final Function<String, String[]> transformationToArrayFunction = line -> line.split(PIPE);

    // Date, IP, Request, Status, User Agent (pipe delimited, open the example file in text editor)
    private final Function<String[], AccessLog> transformationToAccessLogFunction = logs ->
            new AccessLog(
                    logs[1],                                             // IP
                    TransformationService.parseToLocalDateTime(logs[0]), // Date
                    logs[2],                                             // Request
                    Integer.parseInt(logs[3]),                           // Status
                    logs[4]);                                            // User Agent

    private final LoggerRepository loggerRepository;

    @Autowired
    public LoggerService(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    /***
     * Parsing the log file into AccessLog list
     */
    public List<AccessLog> parseFileToAccessLogObject(String fileName) {
        List<AccessLog> result = Collections.emptyList();
        try {
            result = Files.lines(Paths.get(fileName))
                    .filter(linePredicate)                           // filter empty lines
                    .map(transformationToArrayFunction.andThen(transformationToAccessLogFunction))  // split by PIPE (|) then map to AccessLog object
                    .collect(Collectors.toList());                   // collect to the list of AccessLog
            LOGGER.info("Parsing from log file to Access Log successfully completed");
        } catch (IOException io) {
            LOGGER.error("File with filename not found: {}", fileName);
            throw Lombok.sneakyThrow(io);
        }
        return result;
    }


    /***
     * Load all data from log file into database
     */
    public void loadAllDataToDatabase(String fileName) {
        if (loggerRepository.findAll().size() > 0) {
            LOGGER.info("Do nothing. workaround for avoiding new inserts more then 100k, " +
                    "if you are running program more then once without cleaning database.");
        } else {
            // Parse log file(with format: Date|IP|Request|Status|User Agent) to Access log object
            List<AccessLog> logs = parseFileToAccessLogObject(fileName);
            // Save all access logs to the database if not empty accessLogs
            LOGGER.info("Please waiting ... All log data loading into the database. It can take a while :)");

            try {
                CompletableFuture<List<AccessLog>> future
                        = CompletableFuture.supplyAsync(() -> loggerRepository.saveAll(logs));
                List<AccessLog> accessLogs = future.get();
                LOGGER.info("Log date successfully added into database",accessLogs);
            } catch (ExecutionException | InterruptedException ex) {
                LOGGER.info("Something happened : {}", ex);
            }
        }
    }

    /***
     * Retrieves all ip addresses filtered by predefined arguments
     */
    public List<String> findIpAddressesByArguments(LocalDateTime startDate, LocalDateTime endDate, Long threshold) {
        return loggerRepository.findIpAddresses(startDate, endDate, threshold);
    }
}