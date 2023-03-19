package com.narutofood.core.modelmapper;

import com.narutofood.api.model.dto.EnderecoDTO;
import com.narutofood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var enderecoToEnderecoDtoMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoDtoMap.<String>addMapping(src -> src.getCidade().getEstado().getNome(), ((destination, value) ->
                destination.getCidade().setEstado(value)));

        return modelMapper;
    }

}
