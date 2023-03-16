package com.narutofood.api.domain.service;

import com.narutofood.api.domain.exception.NegocioException;
import com.narutofood.api.domain.exception.UsuarioNaoEncontradoException;
import com.narutofood.api.domain.model.Usuario;
import com.narutofood.api.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void changePassword(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = findOrFail(id);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }

    public Usuario findOrFail(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }
}