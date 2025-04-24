package com.labotec.entelgatewayapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<Map<String,Object>> handleException(HttpStatusCodeException ex) {
        return ResponseEntity.status(500)
                .body(Map.of("status", "INTERNAL_SERVER_ERROR",
                        "message", ex.getResponseBodyAsString(),
                        "code", 500));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleExceptionInternal(Exception ex) {
        return ResponseEntity.status(500)
                .body(Map.of("status", "INTERNAL_SERVER_ERROR",
                        "message", ex.getMessage(),
                        "code", 500));
    }
}
