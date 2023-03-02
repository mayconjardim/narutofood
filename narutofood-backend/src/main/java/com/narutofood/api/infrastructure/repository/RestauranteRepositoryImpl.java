package com.narutofood.api.infrastructure.repository;


import com.narutofood.api.domain.model.Restaurante;
import com.narutofood.api.domain.repository.RestauranteRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RestauranteRepositoryImpl implements RestauranteRepository {
    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<Restaurante> findAll() {
        return manager.createQuery("from Restaurante", Restaurante.class)
                .getResultList();
    }
    @Override
    public Restaurante findById(Long id) {
        return manager.find(Restaurante.class, id);
    }

    @Transactional
    @Override
    public Restaurante save(Restaurante restaurante) {
        return manager.merge(restaurante);
    }

    @Transactional
    @Override
    public void remove(Restaurante restaurante) {
        restaurante = findById(restaurante.getId());
        manager.remove(restaurante);
    }

}


