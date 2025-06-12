package com.kairos.pricing.infrastructure.in.rest.exception;

import com.kairos.pricing.domain.expetion.PriceNotFoundException;
import com.kairos.pricing.infrastructure.in.rest.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePriceNotFound(PriceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        String message = String.format("Missing required parameter: %s", ex.getParameterName());
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message;
        if ("date".equals(ex.getName()) && ex.getValue() != null) {
            message = String.format("Invalid date format: '%s'. Expected ISO format: yyyy-MM-dd'T'HH:mm:ss", ex.getValue());
        } else {
            message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
                    ex.getValue(), ex.getName(),
                    ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

}
