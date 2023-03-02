package com.narutofood.api.domain.service;

import com.narutofood.api.domain.exception.EntidadeEmUsoException;
import com.narutofood.api.domain.exception.EntidadeNãoEncontradaException;
import com.narutofood.api.domain.model.Restaurante;
import com.narutofood.api.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Restaurante save(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    public void delete(Long id) {
        try {
            restauranteRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntidadeNãoEncontradaException(String.format("Não existe uma restaurante com código %d", id));
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Restaurante de código %d não pode ser removida, pois está em uso", id));
        }
    }

}
