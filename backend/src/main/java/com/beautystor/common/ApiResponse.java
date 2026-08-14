package com.beautystor.common;

import com.beautystor.enm.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.List;

@Getter
@Schema(description = "Envelope standard de réponse de l'API BeautyStor.")
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
    @Schema(description = "Erreur retournée par l'API.")
    public static record ErrorItem(String message){}
}
