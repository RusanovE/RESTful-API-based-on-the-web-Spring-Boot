package com.example.restfulapiforclearsolutions.controllers.exceptionHendlers;

import com.example.restfulapiforclearsolutions.DTO.ErrorDTO;
import com.example.restfulapiforclearsolutions.controllers.exceptionHendlers.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDTO> handleServerAbstractException(ValidationException e) {
        String message = "The data provided is incorrect";
        log.error("Caught" + e.getClass().getSimpleName());

        return ResponseEntity.status(e.getStatusCode())
                .body(new ErrorDTO(e.getStatusCode().value(), message, e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDTO> handleMissingParameterException(MissingServletRequestParameterException e) {
        log.error("Caught" + e.getClass().getSimpleName());

        return ResponseEntity.status(e.getStatusCode())
                .body(new ErrorDTO(e.getStatusCode().value(), e.getStatusCode().toString(), e.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDTO> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Caught" + e.getClass().getSimpleName());

        return ResponseEntity.status(e.getStatusCode())
                .body(new ErrorDTO(e.getStatusCode().value(), e.getStatusCode().toString(), e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NoResourceFoundException e) {
        log.error("Caught" + e.getClass().getSimpleName());

        return ResponseEntity.status(e.getStatusCode())
                .body(new ErrorDTO(e.getStatusCode().value(), e.getStatusCode().toString(), e.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorDTO> handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("Caught" + e.getClass().getSimpleName());

        return ResponseEntity.status(e.getStatusCode())
                .body(new ErrorDTO(e.getStatusCode().value(), e.getStatusCode().toString(), e.getMessage()));
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            NullPointerException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorDTO> handleBadRequestException(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Some problems with reading the request";
        log.error("Caught" + e.getClass().getSimpleName());

        return ResponseEntity.status(status)
                .body(new ErrorDTO(status.value(), message, e.getMessage()));
    }
}

