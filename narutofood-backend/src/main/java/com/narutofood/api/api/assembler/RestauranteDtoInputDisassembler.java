package com.narutofood.api.api.assembler;

import com.narutofood.api.api.model.input.RestauranteInput;
import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDtoInputDisassembler {

    public Restaurante copyEntityToDTO(RestauranteInput restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;

    }

}
