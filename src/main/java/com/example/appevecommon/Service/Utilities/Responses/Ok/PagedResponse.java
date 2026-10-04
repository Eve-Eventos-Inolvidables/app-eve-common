package com.example.appevecommon.Service.Utilities.Responses.Ok;

import com.example.appevecommon.Service.Utilities.Responses.Pagination;
import com.example.appevecommon.Service.Utilities.Responses.ResponseFactory;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Envelope de un listado paginado.
 * <p>
 * {@code pagination} va al nivel superior y no anidado en {@code data}: {@code data} es el
 * array de items pelado. El tipo parametrizado es el item, no {@code List<D>}, para que el
 * schema exponga el tipo real del recurso y no una lista difuminada.
 *
 * @see ResponseFactory#ok(PageResult)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PagedResponse<T>(boolean success, String message, List<T> data, Pagination pagination) {
}