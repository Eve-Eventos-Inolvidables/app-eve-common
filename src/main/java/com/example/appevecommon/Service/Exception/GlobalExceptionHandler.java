package com.example.appevecommon.Service.Exception;

import com.example.appevecommon.Service.Utilities.Responses.Error.ErrorResponse;
import com.example.appevecommon.Service.Utilities.Responses.ResponseFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //There are distinct cases for the DataIntegrityViolationException
    private static final Map<String, String> DB_ERROR_DICTIONARY = Map.of(
            "violates unique constraint", "Ya existe un registro con los mismos datos únicos.",
            "duplicate key", "Ya existe un registro con los mismos datos únicos.",
            "violates foreign key constraint", "La operación no se puede realizar porque depende de un registro que no existe o está en uso.",
            "violates not-null constraint", "No se enviaron todos los campos obligatorios requeridos por la base de datos.",
            "null value in column", "No se enviaron todos los campos obligatorios requeridos por la base de datos."
    );

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseFactory.notFound(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseFactory.badRequest("Mala petición", message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
         String detailMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return ResponseFactory.badRequest(detailMessage);
    }

    //When a required body isn't provided , this exception is thrown
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNoBody(HttpMessageNotReadableException ex){
        return ResponseFactory.badRequest("Cuerpo no proveido",null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String rootMessage = ex.getMostSpecificCause().getMessage();

        String detailMessage = (rootMessage == null)
                ? "Error de integridad de datos en la base de datos"

                : DB_ERROR_DICTIONARY.entrySet().stream()
                .filter(entry -> rootMessage.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("Error de integridad de datos en la base de datos");

        return ResponseFactory.badRequest(detailMessage);
    }

    // El mensaje se expone a proposito: en desarrollo es lo que sirve para debugear.

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Error no manejado", ex);
        return ResponseFactory.error("Error interno del servidor", ex.getMessage());
    }
}