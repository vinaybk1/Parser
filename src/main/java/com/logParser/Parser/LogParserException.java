package com.logParser.Parser;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LogParserException extends RuntimeException {

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<String> handleRunTimeException(RuntimeException e) {

		return error(HttpStatus.INTERNAL_SERVER_ERROR, e);

	}

	@ExceptionHandler({ IOException.class })

	public ResponseEntity<String> handleDogsServiceException(IOException e) {

		return error(HttpStatus.BAD_REQUEST, e);

	}

	private ResponseEntity<String> error(HttpStatus status, Exception e) {

		return ResponseEntity.status(status).body(e.getMessage());

	}

}