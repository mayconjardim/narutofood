package com.narutofood.api.api.controller;

import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.repository.CozinhaRepository;
import com.narutofood.api.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public Cozinha findById(@PathVariable  Long id) {
        return cadastroCozinhaService.findOrFail(id);
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
    public Cozinha create(@RequestBody @Valid Cozinha cozinha) {
        return cadastroCozinhaService.save(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha update(@PathVariable Long id, @Valid @RequestBody  Cozinha cozinha) {
       Cozinha obj = cadastroCozinhaService.findOrFail(id);
       BeanUtils.copyProperties(cozinha, obj, "id");
       return cadastroCozinhaService.save(obj);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cadastroCozinhaService.delete(id);
    }

}

