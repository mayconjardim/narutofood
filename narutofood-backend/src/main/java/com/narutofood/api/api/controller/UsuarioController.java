package com.narutofood.api.api.controller;

import com.narutofood.api.api.assembler.UsuarioDtoAssembler;
import com.narutofood.api.api.assembler.UsuarioInputDisassembler;
import com.narutofood.api.api.model.dto.UsuarioDTO;
import com.narutofood.api.api.model.input.SenhaInput;
import com.narutofood.api.api.model.input.UsuarioComSenhaInput;
import com.narutofood.api.api.model.input.UsuarioInput;
import com.narutofood.api.domain.model.Usuario;
import com.narutofood.api.domain.repository.UsuarioRepository;
import com.narutofood.api.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioDtoAssembler assembler;

    @Autowired
    private UsuarioInputDisassembler disassembler;

    @GetMapping
    public List<UsuarioDTO> findAll() {
        List<Usuario> todasUsuarios = usuarioRepository.findAll();

        return assembler.toCollectionModel(todasUsuarios);
    }

    @GetMapping("/{id}")
    public UsuarioDTO findById(@PathVariable Long id) {
        Usuario usuario = cadastroUsuario.findOrFail(id);

        return assembler.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO create(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = disassembler.toDomainObject(usuarioInput);
        usuario = cadastroUsuario.save(usuario);

        return assembler.toModel(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioDTO update(@PathVariable Long id,
                                  @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = cadastroUsuario.findOrFail(id);
        disassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.save(usuarioAtual);

        return assembler.toModel(usuarioAtual);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long id, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.changePassword(id, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}    
