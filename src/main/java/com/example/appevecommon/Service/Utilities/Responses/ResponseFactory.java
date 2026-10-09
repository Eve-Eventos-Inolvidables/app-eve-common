package com.example.appevecommon.Service.Utilities.Responses;

import com.example.appevecommon.Service.Utilities.Responses.Error.ErrorDetail;
import com.example.appevecommon.Service.Utilities.Responses.Error.ErrorResponse;
import com.example.appevecommon.Service.Utilities.Responses.Ok.PageResult;
import com.example.appevecommon.Service.Utilities.Responses.Ok.PagedResponse;
import org.springframework.http.ResponseEntity;

/**
 * Constructor de envelopes. Hay un metodo por forma de respuesta, no un metodo generico
 * con flags: asi el tipo de retorno de cada endpoint declara su forma y su schema solo
 * muestra los campos que ese endpoint manda.
 * <p>
 * Los metodos {@code ok} devuelven el envelope pelado porque el status no se elige: es 200.
 * Los metodos de error devuelven {@link ResponseEntity} porque si hay un status que fijar, y
 * fijarlo en la misma llamada que arma el body es lo que garantiza que
 * {@link ErrorDetail#statusCode()} y el status HTTP nunca se contradigan.
 * <p>
 * Para un status de exito que no tiene metodo (201, 202), no agregues uno: usar
 * {@code ResponseEntity.status(201).body(ResponseFactory.ok(dto))}.
 */
public final class ResponseFactory {
    private static final String DEFAULT_SUCCESS_MESSAGE = "Operación exitosa";
    private static final int DEFAULT_ERROR_STATUS = 500;

    private ResponseFactory() {
    }

    // 200 ------------------------------------------------------------------------------
    public static <T> Response<T> ok(T data) {
        return ok(DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static <T> Response<T> ok(String message, T data) {
        return new Response<>(true, message, data);
    }

    /**
     * Aplana el {@link PageResult} del service: los items van a {@code data} y la
     * {@link Pagination} sube al nivel superior del envelope.
     */
    public static <T> PagedResponse<T> ok(PageResult<T> page) {
        return new PagedResponse<>(true, DEFAULT_SUCCESS_MESSAGE, page.items(), page.pagination());
    }

    public static <T> Response<T> resourceCreated(T data) {
        return ok("Recurso creado correctamente", data);
    }

    // 204 ------------------------------------------------------------------------------

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    // ERROR -----------------------------------------------------------------------------
    public static ResponseEntity<ErrorResponse> badRequest(String errorMessage) {
        return failure(400, "Mala petición", errorMessage);
    }
    public static ResponseEntity<ErrorResponse> badRequest(String responseMessage, String errorMessage) {
        return failure(400, responseMessage, errorMessage);
    }

    public static ResponseEntity<ErrorResponse> unauthorized() {
        return failure(401, "No autorizado", "No está autorizado para esta acción");
    }

    public static ResponseEntity<ErrorResponse> notFound(String errorMessage) {
        return failure(404, "Recurso no encontrado", errorMessage);
    }

    public static ResponseEntity<ErrorResponse> error(String responseMessage, String errorMessage) {
        return failure(DEFAULT_ERROR_STATUS, responseMessage, errorMessage);
    }

    public static ResponseEntity<ErrorResponse> error(int statusCode, String responseMessage, String errorMessage) {
        return failure(statusCode, responseMessage, errorMessage);
    }

    // PRIVATE ----------------------------------------------------------------------------
    private static ResponseEntity<ErrorResponse> failure(int statusCode, String responseMessage, String errorMessage) {
        var body = new ErrorResponse(false, responseMessage, new ErrorDetail(statusCode, errorMessage));
        return ResponseEntity.status(statusCode).body(body);
    }
}