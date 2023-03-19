package com.narutofood.api.controller;

import com.narutofood.api.assembler.CozinhaDtoAssembler;
import com.narutofood.api.assembler.CozinhaInputDisassembler;
import com.narutofood.api.model.dto.CozinhaDTO;
import com.narutofood.api.model.input.CozinhaInput;
import com.narutofood.domain.model.Cozinha;
import com.narutofood.domain.repository.CozinhaRepository;
import com.narutofood.domain.service.CadastroCozinhaService;
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
    private CozinhaDtoAssembler assembler;

    @Autowired
    private CozinhaInputDisassembler disassembler;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<CozinhaDTO> findAll() {
        List<Cozinha> todasCozinhas = cozinhaRepository.findAll();

        return assembler.toCollectionModel(todasCozinhas);
    }

    @GetMapping("/{id}")
    public CozinhaDTO findById(@PathVariable Long id) {
        Cozinha cozinha = cadastroCozinhaService.findOrFail(id);

        return assembler.toModel(cozinha);
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
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO create(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = disassembler.toDomainObject(cozinhaInput);
        cozinha = cadastroCozinhaService.save(cozinha);

        return assembler.toModel(cozinha);
    }

    @PutMapping("/{id}")
    public CozinhaDTO atualizar(@PathVariable Long id,
                                  @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinhaService.findOrFail(id);
        disassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cadastroCozinhaService.save(cozinhaAtual);

        return assembler.toModel(cozinhaAtual);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cadastroCozinhaService.delete(id);
    }

}

