package com.narutofood.api.controller;

import com.narutofood.api.assembler.GrupoDtoAssembler;
import com.narutofood.api.model.dto.GrupoDTO;
import com.narutofood.domain.model.Usuario;
import com.narutofood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoDtoAssembler assembler;

    @GetMapping
    public List<GrupoDTO> findAll(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.findOrFail(usuarioId);

        return assembler.toCollectionModel(usuario.getGrupos());
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.addGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.removeGrupo(usuarioId, grupoId);
    }
}  