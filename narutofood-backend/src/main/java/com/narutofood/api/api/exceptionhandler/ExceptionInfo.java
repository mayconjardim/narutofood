package com.narutofood.api.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExceptionInfo {

    private LocalDateTime dataHora;
    private String mensagem;

}
