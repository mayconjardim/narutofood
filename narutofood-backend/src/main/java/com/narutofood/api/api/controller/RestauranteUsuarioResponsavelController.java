package com.narutofood.api.api.controller;

import com.narutofood.api.api.assembler.UsuarioDtoAssembler;
import com.narutofood.api.api.model.dto.UsuarioDTO;
import com.narutofood.api.domain.model.Restaurante;
import com.narutofood.api.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioDtoAssembler assembler;

    @GetMapping
    public List<UsuarioDTO> findAll(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);

        return assembler.toCollectionModel(restaurante.getResponsaveis());
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.removeResponsavel(restauranteId, usuarioId);
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.addResponsavel(restauranteId, usuarioId);
    }
}