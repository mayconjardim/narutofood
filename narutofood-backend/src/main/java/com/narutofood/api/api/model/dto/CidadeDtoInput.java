package com.narutofood.api.api.model.dto;

import com.narutofood.api.api.model.dto.EstadoDtoInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeDtoInput {

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoDtoInput estado;

}
