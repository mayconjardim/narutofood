package com.narutofood.api.api.assembler;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class EstadoDtoInputAssembler {

    @NotBlank
    private String nome;

}
