package com.narutofood.api.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.narutofood.api.domain.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

public abstract class CozinhaMixin {
    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();

}
