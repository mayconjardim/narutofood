package com.narutofood.api.api.assembler;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EstadoDtoIdInputAssembler {

    @NotNull
    private Long id;

}
