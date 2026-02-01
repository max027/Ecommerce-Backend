package com.saurabh.E_Commerce.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError extends RuntimeException{
    private String message;
    private int statusCode;
    public ApiError(String message,int statusCode){
        super(message);
        this.message=message;
        this.statusCode=statusCode;
    }

}
