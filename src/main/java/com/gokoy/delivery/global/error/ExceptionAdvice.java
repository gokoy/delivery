package com.gokoy.delivery.global.error;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gokoy.delivery.global.error.exception.CustomEntityNotFoundException;
import com.gokoy.delivery.global.error.exception.CustomInvalidValueException;
import com.gokoy.delivery.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(CustomEntityNotFoundException.class)
	protected ResponseEntity<?> entityNotFound(HttpServletRequest request, CustomEntityNotFoundException e) {
		ErrorResponse response = new ErrorResponse(e.getErrorCode(), request.getRequestURI());

		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<?> parameterInvalid(HttpServletRequest request, MethodArgumentNotValidException e) {
		List<String> details = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> "[" + fieldError.getField() + "] " + fieldError.getDefaultMessage())
			.collect(Collectors.toList());

		ErrorResponse response = new ErrorResponse(ErrorCode.ARGUMENT_NOT_VALID, request.getRequestURI(), details);

		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	@ExceptionHandler(CustomInvalidValueException.class)
	protected ResponseEntity<?> invalidValue(HttpServletRequest request, CustomInvalidValueException e) {
		ErrorResponse response = new ErrorResponse(e.getErrorCode(), request.getRequestURI());

		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}
}