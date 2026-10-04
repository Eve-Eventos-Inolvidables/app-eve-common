package com.example.appevecommon.Service.Utilities.Responses;

import com.example.appevecommon.Service.Utilities.Responses.Error.ErrorResponse;
import com.example.appevecommon.Service.Utilities.Responses.Ok.PagedResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Operacion exitosa que devuelve un recurso.
 * <p>
 * Es el unico con {@code data}. Los errores usan {@link ErrorResponse} y los listados
 * {@link PagedResponse}: por eso el schema de cada endpoint muestra unicamente los campos
 * que ese endpoint puede emitir.
 *
 * @see ResponseFactory
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response<T>(boolean success, String message, T data) {
}