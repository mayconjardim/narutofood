package com.narutofood.api.api.assembler;

import com.narutofood.api.api.model.dto.FormaPagamentoDTO;
import com.narutofood.api.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> toCollectionModel(List<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }

}
