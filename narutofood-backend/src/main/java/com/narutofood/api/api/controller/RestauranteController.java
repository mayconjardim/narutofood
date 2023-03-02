package com.narutofood.api.api.controller;

import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.model.Restaurante;
import com.narutofood.api.domain.repository.CozinhaRepository;
import com.narutofood.api.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public ResponseEntity<List<Restaurante>> findAll() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Restaurante>> findById(@PathVariable  Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        if(restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }

}
