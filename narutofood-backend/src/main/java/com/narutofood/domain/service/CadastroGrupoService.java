package com.narutofood.domain.service;

import com.narutofood.domain.exception.EntidadeEmUsoException;
import com.narutofood.domain.exception.GrupoNaoEncontradoException;
import com.narutofood.domain.model.Grupo;
import com.narutofood.domain.model.Permissao;
import com.narutofood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroGrupoService {
    private static final String MSG_GRUPO_EM_USO
            = "Grupo de código %d não pode ser removido, pois está em uso";

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissao;

    @Transactional
    public Grupo save(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void delete(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(grupoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, grupoId));
        }
    }

    @Transactional
    public void removePermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = findOrFail(grupoId);
        Permissao permissao = cadastroPermissao.findOrFail(permissaoId);

        grupo.removePermissao(permissao);
    }

    @Transactional
    public void addPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = findOrFail(grupoId);
        Permissao permissao = cadastroPermissao.findOrFail(permissaoId);

        grupo.addPermissao(permissao);
    }



    public Grupo findOrFail(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }
}
