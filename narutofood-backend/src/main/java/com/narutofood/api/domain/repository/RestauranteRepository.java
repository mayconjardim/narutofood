package com.narutofood.api.domain.repository;

import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> findAll();

    Restaurante findById(Long id);

    Restaurante save(Restaurante cozinha);

    void remove(Restaurante cozinha);

}
