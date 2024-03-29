package com.narutofood.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narutofood.api.assembler.RestauranteDtoAssembler;
import com.narutofood.api.assembler.RestauranteInputDisassembler;
import com.narutofood.api.model.dto.RestauranteDTO;
import com.narutofood.api.model.input.RestauranteInput;
import com.narutofood.api.model.view.RestauranteView;
import com.narutofood.domain.exception.CidadeNaoEncontradaException;
import com.narutofood.domain.exception.CozinhaNaoEncontradaException;
import com.narutofood.domain.exception.NegocioException;
import com.narutofood.domain.exception.RestauranteNaoEncontradoException;
import com.narutofood.domain.model.Restaurante;
import com.narutofood.domain.repository.RestauranteRepository;
import com.narutofood.domain.service.CadastroRestauranteService;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
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
    private RestauranteInputDisassembler disassembler;

    @GetMapping
    public MappingJacksonValue findAll(@RequestParam(required = false) String projecao) {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        List<RestauranteDTO> restaurantesDto = assembler.toCollectionDTO(restaurantes);

        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesDto);

        if ("resumo".equals(projecao)) {
            restaurantesWrapper.setSerializationView (RestauranteView.Resumo.class);
        }

        return restaurantesWrapper;
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
           // Restaurante restaurante = disassembler.copyEntityToDTO(restauranteInput);
            Restaurante restauranteAtual = cadastroRestaurante.findOrFail(restauranteId);

            disassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            return assembler.copyDtoToEntity(cadastroRestaurante.save(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
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

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activeAll(@RequestBody  List<Long> restaurandIds) {
        try {
            cadastroRestaurante.activate(restaurandIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactiveAll(@RequestBody  List<Long> restaurandIds) {
        try {
            cadastroRestaurante.deactive(restaurandIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }


    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void open(@PathVariable Long restauranteId) {
        cadastroRestaurante.open(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closed(@PathVariable Long restauranteId) {
        cadastroRestaurante.closed(restauranteId);
    }


    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void active(@PathVariable long id) {
        cadastroRestaurante.activate(id);
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactive(@PathVariable long id) {
        cadastroRestaurante.deactive(id);
    }



}
