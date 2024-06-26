package br.com.vepp.desafiobackvotos.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {

    private int status;

    private String error;

    private String message;

    private long timestamp;

    private String detailMessage;

    private String uriPatch;


}
