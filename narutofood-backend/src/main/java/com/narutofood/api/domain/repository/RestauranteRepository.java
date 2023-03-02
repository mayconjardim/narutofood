package com.narutofood.api.domain.repository;

import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
