package com.narutofood.core.modelmapper;

import com.narutofood.api.model.dto.EnderecoDTO;
import com.narutofood.api.model.input.ItemPedidoInput;
import com.narutofood.domain.model.Endereco;
import com.narutofood.domain.model.ItemPedido;
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

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }

}
