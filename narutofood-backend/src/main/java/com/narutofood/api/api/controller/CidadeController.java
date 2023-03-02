package com.narutofood.api.api.controller;

import com.narutofood.api.domain.exception.EntidadeEmUsoException;
import com.narutofood.api.domain.exception.EntidadeNãoEncontradaException;
import com.narutofood.api.domain.model.Cidade;
import com.narutofood.api.domain.repository.CidadeRepository;
import com.narutofood.api.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public ResponseEntity<List<Cidade>> findAll() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return ResponseEntity.ok(cidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cidade>> findById(@PathVariable  Long id) {
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        if(cidade.isPresent()) {
            return ResponseEntity.ok(cidade);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cidade> create(@RequestBody Cidade cidade) {
        try {
            cadastroCidadeService.save(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EntidadeNãoEncontradaException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody  Cidade cidade) {
        try {
            Cidade obj = cidadeRepository.getReferenceById(id);

            if (obj != null) {
                BeanUtils.copyProperties(cidade, obj, "id");

                obj = cadastroCidadeService.save(obj);
                return ResponseEntity.ok(obj);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNãoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cadastroCidadeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNãoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

