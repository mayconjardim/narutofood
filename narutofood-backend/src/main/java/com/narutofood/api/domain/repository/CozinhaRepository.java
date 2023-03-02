package com.narutofood.api.domain.repository;

import com.narutofood.api.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> list();

    Cozinha buscar(Long id);

    Cozinha salvar(Cozinha cozinha);

    void remover(Cozinha cozinha);

}
