package com.example.appevecommon.Service.Exception;

import com.example.appevecommon.Service.Utilities.Responses.ErrorResponse;
import com.example.appevecommon.Service.Utilities.Responses.Response;
import com.example.appevecommon.Service.Utilities.Responses.ResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response<ErrorResponse>> handleResourceNotFound(ResourceNotFoundException ex) {
        return build(404, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<ErrorResponse>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return build(400, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<ErrorResponse>> handleGeneric(Exception ex) {
        log.error("Error no manejado", ex);
        return build(500, "Error interno del servidor");
    }

    private ResponseEntity<Response<ErrorResponse>> build(int statusCode, String message) {
        return ResponseEntity.status(statusCode).body(ResponseFactory.error(statusCode, message));
    }
}