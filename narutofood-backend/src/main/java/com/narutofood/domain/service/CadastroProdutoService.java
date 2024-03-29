package com.narutofood.domain.service;

import com.narutofood.domain.exception.ProdutoNaoEncontradoException;
import com.narutofood.domain.model.Produto;
import com.narutofood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto findOrFail(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }
}
