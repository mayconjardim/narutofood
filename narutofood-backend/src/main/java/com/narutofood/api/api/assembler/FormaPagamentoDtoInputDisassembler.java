package com.narutofood.api.api.assembler;


import com.narutofood.api.api.model.dto.FormaPagamentoDtoInput;
import com.narutofood.api.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDtoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoDtoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoDtoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }

}
