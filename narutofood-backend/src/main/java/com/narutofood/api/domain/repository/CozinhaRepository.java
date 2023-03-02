package com.narutofood.api.domain.repository;

import com.narutofood.api.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> findAll();

    Cozinha findById(Long id);

    Cozinha save(Cozinha cozinha);

    void remove(Cozinha cozinha);

}
