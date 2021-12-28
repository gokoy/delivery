package com.gokoy.delivery.global.error;

import com.gokoy.delivery.global.error.exception.CustomException;
import com.gokoy.delivery.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<?> customExceptionHandler(HttpServletRequest request, CustomException e) {
        ErrorResponse response = new ErrorResponse(e.getErrorCode(), request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<?> noSuchElementExceptionHandler(HttpServletRequest request, CustomException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.NOT_FOUND, request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Validation Exception
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<?> bindException(HttpServletRequest request, BindException e) {

        List<String> details = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> "[" + fieldError.getField() + "] " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(ErrorCode.ARGUMENT_NOT_VALID, request.getRequestURI(), details);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> constraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.ARGUMENT_NOT_VALID, request.getRequestURI(), Collections.singletonList(e.getMessage()));

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
