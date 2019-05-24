package com.ef;

import com.ef.configuration.Duration;
import com.ef.domain.AccessLog;
import com.ef.repository.LoggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.System.*;

@SpringBootApplication
//@RequiredArgsConstructor
public class ParserApplication implements CommandLineRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(ParserApplication.class);

    private final LoggerRepository loggerRepository;

    @Autowired
    public ParserApplication(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    public static void main(String[] args) {
        LOGGER.info("Starting Parser Application");
        SpringApplication.run(ParserApplication.class, args);
        LOGGER.info("Parser Application Finished");
    }

    // Method #2
    private static void filterFileData(String fileName, LocalDateTime startDate, LocalDateTime endDate, int threshold) {
        try {

            // Date, IP, Request, Status, User Agent (pipe delimited, open the example file in text editor)
            Function<String[], AccessLog> accessLogsFunction = logs ->
                    new AccessLog(logs[0], parseStringToLocalDateTime(logs[1]),
                            logs[2], Integer.parseInt(logs[3]), logs[4]);
            // parse access log file lines to String[]
            List<String[]> stringArrayList = Files.lines(Paths.get(fileName))
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split("|"))
                    .collect(Collectors.toList());

            // Map to AccessLog object
            List<AccessLog> lines = stringArrayList.stream()
                    .map(accessLogsFunction)
                    .collect(Collectors.toList());

            // Filter based on start date, end date, threashold
            long count = lines.stream()
                    .filter(accessLog -> accessLog.getStartDate().isBefore(endDate))
                    .filter(accessLog -> accessLog.getStartDate().isAfter(startDate))
                    .count();

            // need to save in MySql database
            List<AccessLog> collect = lines.stream()
                    .filter(accessLog -> accessLog.getStartDate().isBefore(endDate))
                    .filter(accessLog -> accessLog.getStartDate().isAfter(startDate))
                    .collect(Collectors.toList());
            out.println("<!-----Filtering the file data using Java8 filtering-----!>");
            lines.forEach(out::println);
        } catch (IOException io) {
            LOGGER.info("Executing : command line runner");
        }
    }

    @Override
    public void run(String... args) throws IOException, URISyntaxException {
        //1. Load access file and parse
        //2. Store in table through repository
        //3. Take from arguments and filtering data
        //4. Rendering in console parse and populate AccessLog class from file
        LOGGER.info("Executing : command line runner");
        if (args.length == 4) {
            // Date, IP, Request, Status, User Agent (pipe delimited, open the example file in text editor)
            // Take program arguments from console
            String fileName = args[0];
            LocalDateTime startDate = parseStringToLocalDateTime(args[1]);
            String duration = args[2];
            LocalDateTime endDate = duration.equals(Duration.HOURLY.name()) ? startDate.plusHours(1L) : startDate.plusHours(24L);

            int threshold = Integer.parseInt(args[3]);

            filterFileData(fileName, startDate, endDate, threshold);
            //  Extract article's image,
//			String articleImagePath = crawlerService.extractArticleImage(args[0]);
//			// Extract article's title info.
//			String articleTitleInfo = crawlerService.extractArticleInfo(args[0]);
//			//  Save article info into MySQL database
//			Article currentArticle = storeService.storeArticle(new Article(articleImagePath, articleTitleInfo));
//
//			// Logged already saved Article Information
//			LOGGER.info("Stored Article Info: Id: {}, ImagePath: {}, Title: {}",
//					currentArticle.getArticleId(),
//					currentArticle.getArticleImagePath(),
//					currentArticle.getArticleTitle());
        } else {
            LOGGER.error("Please check that you are passing all arguments: ");
        }
    }

    private static LocalDateTime parseStringToLocalDateTime(String arg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return LocalDateTime.parse(arg, formatter);
    }

	/*
	* ClassLoader classLoader = this.getClass().getClassLoader();
   Path configFilePath = Paths.get(classLoader.getResource("files").toURI());

   List<Path> atrackFileNames = Files.list(configFilePath)
                .filter(s -> s.toString().endsWith(".txt"))
                .map(Path::getFileName)
                .sorted()
                .collect(toList());


    Files.walk(configFilePath)
     .filter(s -> s.toString().endsWith(".txt"))
     .map(Path::getFileName)
     .sorted()
     .collect(Collectors.toList());

    Path start = Paths.get("C:\\data\\");
		try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
		    List<String> collect = stream
		        .map(String::valueOf)
		        .sorted()
		        .collect(Collectors.toList());

		    collect.forEach(System.out::println);
		}


    Date, IP, Request, Status, User Agent (pipe delimited, open the example file in text editor)
    2017-01-01 23:59:59.608|192.168.122.135|"GET / HTTP/1.1"|200|"Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:53.0) Gecko/20100101 Firefox/53.0"



startDate  2017-01-01.15:00:00
ip

* */
}
