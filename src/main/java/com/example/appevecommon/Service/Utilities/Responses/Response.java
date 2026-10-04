package com.example.appevecommon.Service.Utilities.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final Pagination pagination;


    Response(boolean success, String message, T data) {
        this(success, message, data, null);
    }

    Response(boolean success, String message, T data, Pagination pagination) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }
}

