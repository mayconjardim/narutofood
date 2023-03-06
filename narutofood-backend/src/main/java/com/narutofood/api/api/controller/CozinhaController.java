package com.narutofood.api.api.controller;

import com.narutofood.api.domain.exception.EntidadeEmUsoException;
import com.narutofood.api.domain.exception.EntidadeNaoEncontradaException;
import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.repository.CozinhaRepository;
import com.narutofood.api.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/find-name")
    public ResponseEntity<List<Cozinha>> findByName(String nome) {
        List<Cozinha> cozinhas = cozinhaRepository.findByNomeContaining(nome);
        if(cozinhas.size() > 0) {
            return ResponseEntity.ok(cozinhas);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cozinha> create(@RequestBody Cozinha cozinha) {
        try {
            cadastroCozinhaService.save(cozinha);
            return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody  Cozinha cozinha) {
        try {
            Cozinha obj = cozinhaRepository.getReferenceById(id);

            if (obj != null) {
                BeanUtils.copyProperties(cozinha, obj, "id");

                obj = cadastroCozinhaService.save(obj);
                return ResponseEntity.ok(obj);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cadastroCozinhaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

