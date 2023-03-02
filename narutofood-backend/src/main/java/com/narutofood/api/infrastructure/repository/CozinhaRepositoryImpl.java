package com.narutofood.api.infrastructure.repository;

import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.repository.CozinhaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CozinhaRepositoryImpl implements CozinhaRepository {
    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<Cozinha> findAll() {
        return manager.createQuery("from Cozinha", Cozinha.class)
                .getResultList();
    }
    @Override
    public Cozinha findById(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    @Override
    public Cozinha save(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    @Transactional
    @Override
    public void remove(Cozinha cozinha) {
        cozinha = findById(cozinha.getId());
        manager.remove(cozinha);
    }

}


