package com.narutofood.api.domain.service;

import com.narutofood.api.domain.exception.NegocioException;
import com.narutofood.api.domain.exception.UsuarioNaoEncontradoException;
import com.narutofood.api.domain.model.Grupo;
import com.narutofood.api.domain.model.Usuario;
import com.narutofood.api.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Transactional
    public Usuario save(Usuario usuario) {

        //Checar
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(
                    String.format("Já existe um usuário cadastrado com o e-mail", usuario.getEmail()));
        }

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

    public void removeGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = findOrFail(usuarioId);
        Grupo grupo = cadastroGrupo.findOrFail(grupoId);

        usuario.removeGrupo(grupo);
    }

    @Transactional
    public void addGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = findOrFail(usuarioId);
        Grupo grupo = cadastroGrupo.findOrFail(grupoId);

        usuario.addGrupo(grupo);
    }

    public Usuario findOrFail(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }
}
