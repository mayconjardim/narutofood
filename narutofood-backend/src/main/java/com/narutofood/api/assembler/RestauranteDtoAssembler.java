package com.narutofood.api.assembler;

import com.narutofood.api.model.dto.RestauranteDTO;
import com.narutofood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTO copyDtoToEntity(Restaurante restaurante) {
      return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(restaurante -> copyDtoToEntity(restaurante)).collect(Collectors.toList());
    }

}
