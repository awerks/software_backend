package com.seproject.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    /* ───── Validation errors (JSR-303) → 400 ─────────────────────────────── */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleValidation(BindException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, String> details = new HashMap<>();
        for (FieldError f : fieldErrors) details.put(f.getField(), f.getDefaultMessage());

        return ResponseEntity.badRequest().body(Map.of(
            "error", "Validation failed",
            "details", details
        ));
    }

    /* ───── Resource not found → 404 ──────────────────────────────────────── */ 
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("error", ex.getMessage()));
    }



    /*  
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(Map.of("error", ex.getMessage()));
    }
    */
}
