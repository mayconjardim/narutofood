package com.narutofood.api.domain.service;

import com.narutofood.api.domain.exception.PermissaoNaoEncontradaException;
import com.narutofood.api.domain.model.Permissao;
import com.narutofood.api.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao findOrFail(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
