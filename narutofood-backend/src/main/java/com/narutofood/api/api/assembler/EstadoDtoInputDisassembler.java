package com.narutofood.api.api.assembler;

import com.narutofood.api.api.model.dto.EstadoDtoInput;
import com.narutofood.api.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDtoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(EstadoDtoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToDomainObject(EstadoDtoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }

}
