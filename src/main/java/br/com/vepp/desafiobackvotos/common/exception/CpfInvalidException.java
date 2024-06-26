package br.com.vepp.desafiobackvotos.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CpfInvalidException extends RuntimeException {

    public CpfInvalidException(String msg) {
        super(msg);
    }
}
