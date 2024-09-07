package com.example.shoes_ecommerce.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String  maxSize;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<?, ?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldErrorResponse> fieldErrorResponses = new ArrayList<>();
        e.getFieldErrors().forEach(fieldError -> fieldErrorResponses
                .add(FieldErrorResponse.builder()
                        .field(fieldError.getField())
                        .detail(fieldError.getDefaultMessage())
                        .build()));

        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .reason(fieldErrorResponses)
                .build();

        return Map.of("error", errorResponse);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<?, ?> handleSqlExceptionHelperException(DataIntegrityViolationException e) {

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Product already exists");
        response.put("message", e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage());
        response.put("status", HttpStatus.CONFLICT.value());

        return response;
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode().value())
                .reason(e.getReason())
                .build();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(Map.of("error", errorResponse));
    }

    @ExceptionHandler(MailSendException.class)
    @ResponseBody
    public String handleMailSendException(MailSendException ex) {
        // Log the exception and return a custom error message or handle it accordingly
        ex.printStackTrace();
        return "Error sending email: " + ex.getMessage();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    Map<?,?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {

        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .code(ex.getStatusCode().value())
                .reason(ex.getMessage()+ ":"+ maxSize)
                .build();
        return Map.of("error", ex.getMessage());
    }

}
