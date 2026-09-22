package com.example.appevecommon.Service.Utilities.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private final boolean success;
    private final String message;
    private final T data;


    Response(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}