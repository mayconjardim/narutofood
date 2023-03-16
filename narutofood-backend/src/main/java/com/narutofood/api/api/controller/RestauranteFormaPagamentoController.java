package com.narutofood.api.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narutofood.api.api.assembler.FormaPagamentoDtoAssembler;
import com.narutofood.api.api.assembler.RestauranteDtoAssembler;
import com.narutofood.api.api.assembler.RestauranteInputDisassembler;
import com.narutofood.api.api.model.dto.FormaPagamentoDTO;
import com.narutofood.api.api.model.dto.RestauranteDTO;
import com.narutofood.api.api.model.input.RestauranteInput;
import com.narutofood.api.domain.exception.CidadeNaoEncontradaException;
import com.narutofood.api.domain.exception.CozinhaNaoEncontradaException;
import com.narutofood.api.domain.exception.NegocioException;
import com.narutofood.api.domain.model.Restaurante;
import com.narutofood.api.domain.repository.RestauranteRepository;
import com.narutofood.api.domain.service.CadastroRestauranteService;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
