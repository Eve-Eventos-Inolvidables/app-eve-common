package com.example.appevecommon.Service.Utilities.Responses.Error;

import com.example.appevecommon.Service.Utilities.Responses.ResponseFactory;

/**
 * Detalle de una operacion fallida. Va dentro de {@link ErrorResponse#error()}.
 * <p>
 * {@code statusCode} siempre coincide con el status HTTP de la respuesta: lo fija
 * {@link ResponseFactory}, que construye el {@code ResponseEntity} y el envelope en la
 * misma llamada.
 */
public record ErrorDetail(int statusCode, String message) {
}