package com.rubanj.casino.rockpaperscissors.rest;

import com.rubanj.casino.rockpaperscissors.exception.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.NoSuchElementException;


@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createErrorResponse("Validation Failed", ex.getBindingResult().toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        log.error("Unexpected error", ex);
        return createErrorResponse(ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public final ResponseEntity<Object> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, WebRequest request) {
        return createErrorResponse(ex.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return createErrorResponse(ex.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public final ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        return createErrorResponse(ex.getMessage(), request.getDescription(false), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        return createErrorResponse(ex.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return createErrorResponse(ex.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND);
    }

    private static ResponseEntity<Object> createErrorResponse(String message, String description, HttpStatus notFound) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, description);
        return new ResponseEntity<>(errorDetails, notFound);
    }

}
