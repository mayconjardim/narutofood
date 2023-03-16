package com.narutofood.api.domain.service;

import com.narutofood.api.domain.exception.RestauranteNaoEncontradoException;
import com.narutofood.api.domain.model.*;
import com.narutofood.api.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroRestauranteService {

    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
            = "Não existe um cadastro de restaurante com código %d";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Transactional
    public Restaurante save(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        long cidadeId = restaurante.getEndereco().getCidade().getId();
        Cozinha cozinha = cadastroCozinha.findOrFail(cozinhaId);
        Cidade cidade = cadastroCidade.findOrFail(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void activate(Long id ){
        Restaurante restauranteAtual = findOrFail(id);
       restauranteAtual.active();
    }

    @Transactional
    public void deactive(Long id ){
        Restaurante restauranteAtual = findOrFail(id);
        restauranteAtual.deactive();
    }

    @Transactional
    public void removeFormaPagamento(Long id, long formaPagamentoId) {
        Restaurante restaurante = findOrFail(id);
        FormaPagamento formaPagamento = cadastroFormaPagamento.findOrFail(formaPagamentoId);
        restaurante.removeFormaPagamento(formaPagamento);
    }

    @Transactional
    public void open(Long restauranteId) {
        Restaurante restauranteAtual = findOrFail(restauranteId);

        restauranteAtual.open();
    }

    @Transactional
    public void closed(Long restauranteId) {
        Restaurante restauranteAtual = findOrFail(restauranteId);

        restauranteAtual.closed();
    }

    @Transactional
    public void removeResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = findOrFail(restauranteId);
        Usuario usuario = cadastroUsuario.findOrFail(usuarioId);

        restaurante.removeResponsavel(usuario);
    }

    @Transactional
    public void addResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = findOrFail(restauranteId);
        Usuario usuario = cadastroUsuario.findOrFail(usuarioId);

        restaurante.addResponsavel(usuario);
    }


    public Restaurante findOrFail(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
    }

}
