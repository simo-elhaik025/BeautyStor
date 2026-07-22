package com.beautystor.common;

import com.beautystor.enm.ResponseStatus;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Getter
public class ApiResponse <T> {
    private  ResponseStatus status;
    private  T data;
    private List<ErrorItem> errors;


    public ApiResponse(T data) {
        this.status = ResponseStatus.SUCCESS;
        this.data = data;
        this.errors =  null;
    }
    public ApiResponse(List<ErrorItem> errors) {
        this.status = ResponseStatus.ERROR;
        this.errors = errors;
        this.data = null;
    }
    public static record ErrorItem(String message){}
}
