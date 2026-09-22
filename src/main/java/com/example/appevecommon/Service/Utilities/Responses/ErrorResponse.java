package com.example.appevecommon.Service.Utilities.Responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int statusCode;
    private String message;
    public ErrorResponse(int statusCode, String message){
        this.message=message;
        this.statusCode=statusCode;
    }
}
