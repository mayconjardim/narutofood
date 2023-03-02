package com.narutofood.api.api.controller;

import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public ResponseEntity<List<Cozinha>> findAll() {
        List<Cozinha> cozinhas = cozinhaRepository.findAll();
        return ResponseEntity.ok(cozinhas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cozinha>> findById(@PathVariable  Long id) {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
        if(cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha);
        }
        return ResponseEntity.notFound().build();
    }

}
