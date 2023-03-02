package com.narutofood.api.api.controller;

import com.narutofood.api.domain.exception.EntidadeEmUsoException;
import com.narutofood.api.domain.exception.EntidadeNãoEncontradaException;
import com.narutofood.api.domain.model.Restaurante;
import com.narutofood.api.domain.model.Restaurante;
import com.narutofood.api.domain.repository.RestauranteRepository;
import com.narutofood.api.domain.repository.RestauranteRepository;
import com.narutofood.api.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public ResponseEntity<List<Restaurante>> findAll() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Restaurante>> findById(@PathVariable  Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        if(restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public void create(@RequestBody Restaurante restaurante) {
        cadastroRestauranteService.save(restaurante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> update(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Restaurante obj = restauranteRepository.getReferenceById(id);

        if (obj != null) {
            BeanUtils.copyProperties(restaurante, obj, "id");
            obj = restauranteRepository.save(obj);
            return ResponseEntity.ok(obj);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cadastroRestauranteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNãoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
