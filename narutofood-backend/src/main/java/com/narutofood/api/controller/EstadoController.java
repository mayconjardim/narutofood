package com.narutofood.api.controller;

import com.narutofood.api.assembler.EstadoDtoAssembler;
import com.narutofood.api.assembler.EstadoInputDisassembler;
import com.narutofood.api.model.dto.EstadoDTO;
import com.narutofood.api.model.input.EstadoInput;
import com.narutofood.domain.exception.EntidadeEmUsoException;
import com.narutofood.domain.exception.EntidadeNaoEncontradaException;
import com.narutofood.domain.model.Estado;
import com.narutofood.domain.repository.EstadoRepository;
import com.narutofood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoDtoAssembler assembler;

    @Autowired
    private EstadoInputDisassembler disassembler;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<EstadoDTO> findAll() {
        List<Estado> estados = estadoRepository.findAll();
        return assembler.toCollectionModel(estados);
    }

    @GetMapping("/{id}")
    public EstadoDTO findById(@PathVariable  Long id) {
      Estado estado = cadastroEstadoService.findOrFail(id);
      return assembler.toModel(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO create(@RequestBody @Valid EstadoInput estadoInput) {
      Estado estado = disassembler.toDomainObject(estadoInput);

      estado = cadastroEstadoService.save(estado);

      return assembler.toModel(estado);
    }

    @PutMapping("/{id}")
    public EstadoDTO atualizar(@PathVariable Long id,
                                 @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstadoService.findOrFail(id);

        disassembler.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = cadastroEstadoService.save(estadoAtual);

        return assembler.toModel(estadoAtual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cadastroEstadoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

