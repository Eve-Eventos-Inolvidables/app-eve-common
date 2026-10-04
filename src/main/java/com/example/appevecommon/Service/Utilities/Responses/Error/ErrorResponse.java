package com.example.appevecommon.Service.Utilities.Responses.Error;

import com.example.appevecommon.Service.Utilities.Responses.ResponseFactory;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Envelope de una operacion fallida. No lleva {@code data}: el detalle va en {@code error}.
 * <p>
 * Los factories de {@link ResponseFactory} devuelven este tipo ya envuelto en su
 * {@code ResponseEntity}, asi que el status HTTP y {@link ErrorDetail#statusCode()} se fijan
 * en la misma llamada y no pueden contradecirse.
 *
 * @see ErrorDetail
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(boolean success, String message, ErrorDetail error) {
}