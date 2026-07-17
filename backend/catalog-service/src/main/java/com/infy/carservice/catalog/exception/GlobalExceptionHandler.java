package com.infy.carservice.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex)
	{
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->{
			errors.put(error.getField(), error.getDefaultMessage());
			});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRuntimeExceptions(RuntimeException ex) {
		Map<String, String> response = new HashMap<>();
		String message = ex.getMessage();
		response.put("message", message);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (message != null && (message.contains("Invalid credentials") || message.contains("not found"))) {
			status = HttpStatus.UNAUTHORIZED;

		}
		return new ResponseEntity<>(response, status);
		}
}
