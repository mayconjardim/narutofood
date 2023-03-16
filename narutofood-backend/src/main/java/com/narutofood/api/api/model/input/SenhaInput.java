package com.narutofood.api.api.model.input;

import javax.validation.constraints.NotBlank;

public class SenhaInput {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String novaSenha;

}
