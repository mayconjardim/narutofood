package com.narutofood.api.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narutofood.api.api.assembler.RestauranteDtoAssembler;
import com.narutofood.api.api.assembler.RestauranteDtoInputDisassembler;
import com.narutofood.api.api.model.dto.RestauranteDTO;
import com.narutofood.api.api.model.input.RestauranteInput;
import com.narutofood.api.domain.exception.CozinhaNaoEncontradaException;
import com.narutofood.api.domain.exception.NegocioException;
import com.narutofood.api.domain.model.Restaurante;
import com.narutofood.api.domain.repository.RestauranteRepository;
import com.narutofood.api.domain.service.CadastroRestauranteService;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteDtoAssembler assembler;

    @Autowired
    private RestauranteDtoInputDisassembler disassembler;

    @GetMapping
    public List<RestauranteDTO> findAll() {
        return assembler.toCollectionDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO findById(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);
        return assembler.copyDtoToEntity(restaurante);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

        try {
            Restaurante restaurante = disassembler.copyEntityToDTO(restauranteInput);
            return assembler.copyDtoToEntity(cadastroRestaurante.save(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO update(@PathVariable Long restauranteId,
                                 @RequestBody @Valid RestauranteInput restauranteInput ) {
        try {
            Restaurante restaurante = disassembler.copyEntityToDTO(restauranteInput);
            Restaurante restauranteAtual = cadastroRestaurante.findOrFail(restauranteId);

            BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return assembler.copyDtoToEntity(cadastroRestaurante.save(restauranteAtual));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
                       HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }




}
