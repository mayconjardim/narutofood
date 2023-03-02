package com.narutofood.api.domain.exception;

public class EntidadeNãoEncontradaException extends RuntimeException {

    public EntidadeNãoEncontradaException(String mensagem) {
        super(mensagem);
    }

}