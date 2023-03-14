package com.narutofood.api.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class EstadoDtoInput {

    @NotBlank
    private String nome;

}
