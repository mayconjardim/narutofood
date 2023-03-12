package com.narutofood.api.api.assembler;

import com.narutofood.api.api.model.input.RestauranteInput;
import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDtoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante copyEntityToDTO(RestauranteInput restauranteInput) {
      return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteInput, restaurante);
    }

}
