package com.narutofood.api.domain.service;

import com.narutofood.api.domain.exception.EntidadeEmUsoException;
import com.narutofood.api.domain.exception.EntidadeNãoEncontradaException;
import com.narutofood.api.domain.model.Cidade;
import com.narutofood.api.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade save(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public void delete(Long id) {
        try {
            cidadeRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntidadeNãoEncontradaException(String.format("Não existe uma cidade com código %d", id));
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida, pois está em uso", id));
        }
    }

}
