package com.narutofood.api.api.assembler;

import com.narutofood.api.api.model.dto.CozinhaDTO;
import com.narutofood.api.api.model.dto.RestauranteDTO;
import com.narutofood.api.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDtoAssembler {

    public RestauranteDTO copyDtoToEntity(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();

        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDTO.setCozinha(cozinhaDTO);
        return restauranteDTO;
    }

    public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(restaurante -> copyDtoToEntity(restaurante)).collect(Collectors.toList());
    }

}
