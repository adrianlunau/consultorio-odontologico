package com.dh.clinica.exception;

import com.dh.clinica.dto.ErrorResponse;
import org.hibernate.hql.internal.ast.ErrorReporter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {
        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Entity not found",
                List.of(e.getMessage()));
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleOperationNotPermitted(OperationNotPermittedException e) {
        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.NOT_ACCEPTABLE,
                "This action cannot be performed.",
                List.of(e.getMessage()));
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }
    private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String message, List<String> moreInfo) {
        return ErrorResponse.builder()
                .statusCode(httpStatus.value())
                .message(message)
                .moreInfo(moreInfo)
                .build();
    }
}
