package com.narutofood.api.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EstadoDtoIdInput {

    @NotNull
    private Long id;

}
