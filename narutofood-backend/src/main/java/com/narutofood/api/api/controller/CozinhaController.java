package com.narutofood.api.api.controller;

import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.repository.CozinhaRepository;
import com.narutofood.api.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

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

    @PostMapping
    public void create(@RequestBody Cozinha cozinha) {
        cadastroCozinhaService.save(cozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> update(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        Cozinha obj = cozinhaRepository.getReferenceById(id);

        if (obj != null) {
            BeanUtils.copyProperties(cozinha, obj, "id");
            obj = cozinhaRepository.save(obj);
            return ResponseEntity.ok(obj);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cozinha> delete(@PathVariable  Long id) {
        try {
            Cozinha obj = cozinhaRepository.getReferenceById(id);
            if (obj != null) {
                cozinhaRepository.delete(obj);
            }
        } catch (DataIntegrityViolationException e) {
            ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }

}

