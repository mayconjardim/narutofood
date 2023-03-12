package com.narutofood.api.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.narutofood.api.api.model.mixin.RestauranteMixin;
import com.narutofood.api.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }


}
