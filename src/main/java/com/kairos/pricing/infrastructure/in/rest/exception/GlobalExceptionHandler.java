package com.kairos.pricing.infrastructure.in.rest.exception;

import com.kairos.pricing.domain.expetion.PriceNotFoundException;
import com.kairos.pricing.domain.expetion.RequestParamException;
import com.kairos.pricing.infrastructure.in.rest.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePriceNotFound(PriceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage()));
    }
    @ExceptionHandler(RequestParamException.class)
    public ResponseEntity<ErrorResponse> handleRequestParamException(RequestParamException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

}
