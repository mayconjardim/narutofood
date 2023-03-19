package com.narutofood.api.controller;

import com.narutofood.api.assembler.PermissaoDtoAssembler;
import com.narutofood.api.model.dto.PermissaoDTO;
import com.narutofood.domain.model.Grupo;
import com.narutofood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoDtoAssembler assembler;

    @GetMapping
    public List<PermissaoDTO> findAll(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.findOrFail(grupoId);

        return assembler.toCollectionModel(grupo.getPermissoes());
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.removePermissao(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.addPermissao(grupoId, permissaoId);
    }

}
