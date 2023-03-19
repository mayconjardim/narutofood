package com.narutofood.api.controller;

import com.narutofood.api.assembler.FormaPagamentoDtoAssembler;
import com.narutofood.api.model.dto.FormaPagamentoDTO;
import com.narutofood.domain.model.Restaurante;
import com.narutofood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{id}/forma-pagamento")
public class RestauranteFormaPagamentoController {


    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private FormaPagamentoDtoAssembler assembler;

    @GetMapping
    public List<FormaPagamentoDTO> findAll(@PathVariable Long id) {

        Restaurante restaurante = cadastroRestaurante.findOrFail(id);

        return assembler.toCollectionModel(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFormaPagamento(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.removeFormaPagamento(id, formaPagamentoId);
    }




}
