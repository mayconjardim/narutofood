package com.narutofood.api.api.controller;

import com.narutofood.api.api.assembler.CidadeDtoAssembler;
import com.narutofood.api.api.assembler.CidadeDtoInputDisassembler;
import com.narutofood.api.api.model.dto.CidadeDTO;
import com.narutofood.api.api.model.input.CidadeInput;
import com.narutofood.api.domain.exception.*;
import com.narutofood.api.domain.model.Cidade;
import com.narutofood.api.domain.repository.CidadeRepository;
import com.narutofood.api.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeDtoAssembler assembler;

    @Autowired
    private CidadeDtoInputDisassembler disassembler;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<CidadeDTO> findAll() {
        List<Cidade> cidades = cidadeRepository.findAll();

        return assembler.toCollectionModel(cidades);
    }

    @GetMapping("/{id}")
    public CidadeDTO findById(@PathVariable Long id) {
        Cidade cidade = cadastroCidadeService.findOrFail(id);

        return assembler.toModel(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO create(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = disassembler.toDomainObject(cidadeInput);

            cidade = cadastroCidadeService.save(cidade);

            return assembler.toModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CidadeDTO update(@PathVariable Long id,
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.findOrFail(id);

            disassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidadeService.save(cidadeAtual);

            return assembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cadastroCidadeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (CidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEstadoNãoEcontradaException(EntidadeNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}

