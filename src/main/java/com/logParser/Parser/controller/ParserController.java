package com.logParser.Parser.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParserController {

	@Value("${parserfile.location}")
	private String path;
	
	Logger logger = LoggerFactory.getLogger(ParserController.class);

	@GetMapping(path = "/getTotalRequests", produces = "application/json")
	public Long getTotalRequests() throws IOException {
		logger.debug("fetching getTotalRequests::");
		long numberOfReq = 0;
			Stream<String> stream = Files.lines(Paths.get(path));
			numberOfReq = stream.filter(line -> line.startsWith("REQUEST")).filter(line -> !line.contains("\tKO\t"))
					.count();
				stream.close();

		logger.debug("Total number of Valid Requests::" + numberOfReq);

		return numberOfReq;
	}

	@GetMapping(path = "/getFailedRequests", produces = "application/json")
	public Long getFailedRequests() throws IOException {
		long numberOfReq = 0;
			Stream<String> stream = Files.lines(Paths.get(path));
			Stream<String> reqStream = Files.lines(Paths.get(path));
			numberOfReq = stream.count() - reqStream.filter(line -> line.startsWith("REQUEST"))
					.filter(line -> !line.contains("\tKO\t")).count();// collect(Collectors.toList());
			stream.close();
			reqStream.close();
		logger.debug("Total number of Failed Requests::" + numberOfReq);

		return numberOfReq;
	}

	@GetMapping(path = "/get95Percentile", produces = "application/json")
	public double get95Percentile() throws IOException {
		Long requestsCount = 0l;
			Stream<String> stream = Files.lines(Paths.get(path));
			requestsCount = stream.filter(line -> line.startsWith("REQUEST")).filter(line -> !line.contains("\tKO\t"))
					.count();
			stream.close();
		logger.debug("Average of Valid Requests::" + requestsCount);

		return 0.95 * requestsCount;
	}

	@GetMapping(path = "/get99Percentile", produces = "application/json")
	public double get99Percentile() throws IOException {
			Stream<String> stream = Files.lines(Paths.get(path));
			Long requestsCount = stream.filter(line -> line.startsWith("REQUEST")).filter(line -> !line.contains("\tKO\t"))
					.count();
			stream.close();

		logger.debug("Average of Valid Requests::" + requestsCount);

		return 0.99 * requestsCount;
	}

	@GetMapping(path = "/getAverageResponseTime", produces = "application/json")
	public Double getAverageResponseTime() throws IOException {
			Stream<String> stream = Files.lines(Paths.get(path));
			List<String> requestlines = stream.filter(line -> line.startsWith("REQUEST"))
					.filter(line -> !line.contains("\tKO\t")).collect(Collectors.toList());

			List<Long> list = new ArrayList<Long>();
			for (String line : requestlines) {
				list.add(Stream.of(line.split("\t")).skip(4).limit(2).map(Long::parseLong).reduce(0l, (a, b) -> b - a));
			}

			OptionalDouble avg = list.stream().mapToDouble(a -> a).average();
			Double average = avg.getAsDouble();
				stream.close();
		logger.debug("Average of Valid Requests::" + average);

		return average;
	}

	@ExceptionHandler({ Exception.class, IOException.class })
	public ResponseEntity<Object> handleException() {
		return new ResponseEntity<Object>("Unable to process the request", HttpStatus.BAD_REQUEST);
	}

}
