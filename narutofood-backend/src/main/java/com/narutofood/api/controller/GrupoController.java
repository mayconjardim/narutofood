package com.narutofood.api.controller;

import com.narutofood.api.assembler.GrupoDtoAssembler;
import com.narutofood.api.assembler.GrupoInputDisassembler;
import com.narutofood.api.model.dto.GrupoDTO;
import com.narutofood.api.model.input.GrupoInput;
import com.narutofood.domain.model.Grupo;
import com.narutofood.domain.repository.GrupoRepository;
import com.narutofood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoDtoAssembler assembler;

    @Autowired
    private GrupoInputDisassembler disassembler;

    @GetMapping
    public List<GrupoDTO> findAll() {
        List<Grupo> todosGrupos = grupoRepository.findAll();

        return assembler.toCollectionModel(todosGrupos);
    }

    @GetMapping("/{id}")
    public GrupoDTO findById(@PathVariable Long id) {
        Grupo grupo = cadastroGrupo.findOrFail(id);

        return assembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO create(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = disassembler.toDomainObject(grupoInput);

        grupo = cadastroGrupo.save(grupo);

        return assembler.toModel(grupo);
    }

    @PutMapping("/{id}")
    public GrupoDTO update(@PathVariable Long id,
                                @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupo.findOrFail(id);

        disassembler.copyToDomainObject(grupoInput, grupoAtual);

        grupoAtual = cadastroGrupo.save(grupoAtual);

        return assembler.toModel(grupoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroGrupo.delete(id);
    }
}
