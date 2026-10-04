package com.example.appevecommon.Service.Utilities.Responses;

import java.util.List;

public final class ResponseFactory {
    private static final String DEFAULT_SUCCESS_MESSAGE = "Operación exitosa";
    private static final int DEFAULT_ERROR_STATUS = 500;

    private ResponseFactory() {
    }

    //OK
    public static <T> Response<T> ok(T data) {
        return new Response<>(true, null, data);
    }

    public static <T> Response<T> ok(String message, T data) {
        return new Response<>(true, message, data);
    }

    public static <T> Response<List<T>> ok(PageResult<T> data) {
        return new Response<>(true, null, data.items(), data.pagination());
    }

    //NOT OK
    public static Response<ErrorResponse> error(String responseMessage, String errorMessage) {
        return new Response<>(false, responseMessage, new ErrorResponse(DEFAULT_ERROR_STATUS, errorMessage));
    }

    public static Response<ErrorResponse> error(int statusCode, String responseMessage, String errorMessage) {
        return new Response<>(false, responseMessage, new ErrorResponse(statusCode,errorMessage));
    }
}