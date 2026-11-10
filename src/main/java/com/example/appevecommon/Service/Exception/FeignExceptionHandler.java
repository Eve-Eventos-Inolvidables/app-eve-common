package com.example.appevecommon.Service.Exception;

import com.example.appevecommon.Service.Utilities.Responses.Error.ErrorResponse;
import com.example.appevecommon.Service.Utilities.Responses.ResponseFactory;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class FeignExceptionHandler {

    private static final String USER_LOOKUP_ERROR = "No se pudo obtener el usuario consultado";

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleFeignNotFound(FeignException.NotFound ex) {
        log.warn("El usuario consultado no fue encontrado en el servicio de usuarios");
        return ResponseFactory.notFound(USER_LOOKUP_ERROR);
    }

    @ExceptionHandler({FeignException.ServiceUnavailable.class, RetryableException.class})
    public ResponseEntity<ErrorResponse> handleFeignUnavailable(Exception ex) {
        log.error("Servicio de usuarios no disponible al consultar el usuario", ex);
        return ResponseFactory.error(HttpStatus.SERVICE_UNAVAILABLE.value(), USER_LOOKUP_ERROR, null);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex) {
        log.error("Error al consultar el usuario en el servicio de usuarios (status {})", ex.status(), ex);
        return ResponseFactory.error(HttpStatus.SERVICE_UNAVAILABLE.value(), USER_LOOKUP_ERROR, null);
    }
}
