package com.ef.service;

import com.ef.domain.AccessLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LoggerService {
    private static Logger LOGGER = LoggerFactory.getLogger(LoggerService.class);

    public List<AccessLog> parseFileToAccessLogObject(String fileName) {
        List<AccessLog> result = Collections.emptyList();
        try {
            // Date, IP, Request, Status, User Agent (pipe delimited, open the example file in text editor)
            Function<String[], AccessLog> accessLogsFunction = logs ->
                    new AccessLog(logs[1],
                            parseStringToLocalDateTime(logs[0]),
                            logs[2],
                            Integer.parseInt(logs[3]),
                            logs[4]);

            result = Files.lines(Paths.get(fileName))
                    .filter(line -> !line.isEmpty())                     // filter empty lines
                    .map(line -> line.split("\\|"))                // split by pipe (|)
                    .map(accessLogsFunction)                             // transfer to AccessLog object
                    .collect(Collectors.toList());                       // collect to the list of AccessLog
            LOGGER.info("<!-----Filtering the file data using Java8 filtering-----!>");
         } catch (IOException io) {
            LOGGER.info("Huston something is happened ...");
        }
        return result;
    }

    public static LocalDateTime parseStringToLocalDateTime(String arg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return LocalDateTime.parse(arg, formatter);
    }
}
