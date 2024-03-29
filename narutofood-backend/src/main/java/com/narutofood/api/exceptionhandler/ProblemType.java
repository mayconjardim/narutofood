package com.narutofood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");




    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://narutofood.web.app" + path;
        this.title = title;
    }

}
