package com.narutofood.api.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.narutofood.api.domain.model.Estado;

public abstract class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;

}
