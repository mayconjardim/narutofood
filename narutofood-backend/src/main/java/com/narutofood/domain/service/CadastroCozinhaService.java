package com.narutofood.domain.service;

import com.narutofood.domain.exception.CozinhaNaoEncontradaException;
import com.narutofood.domain.exception.EntidadeEmUsoException;
import com.narutofood.domain.model.Cozinha;
import com.narutofood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroCozinhaService {

    public static final String NÃO_EXISTE_UMA_COZINHA_COM_CÓDIGO_D = "Não existe uma cozinha com código %d";
    public static final String COZINHA_DE_CÓDIGO_D_NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha save(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void delete(Long id) {
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();
        }
        catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(String.format(NÃO_EXISTE_UMA_COZINHA_COM_CÓDIGO_D, id));
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(COZINHA_DE_CÓDIGO_D_NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO, id));
        }
    }

    public Cozinha findOrFail(Long id) {
        return cozinhaRepository.findById(id).orElseThrow( () -> new CozinhaNaoEncontradaException(String.format(NÃO_EXISTE_UMA_COZINHA_COM_CÓDIGO_D, id)));
    }


}
