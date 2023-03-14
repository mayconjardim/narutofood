package com.narutofood.api.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDTO {


    private String cep;


    private String rua;


    private String numero;


    private String complemento;


    private String bairro;

    private CidadeResumoDTO cidade;


}
