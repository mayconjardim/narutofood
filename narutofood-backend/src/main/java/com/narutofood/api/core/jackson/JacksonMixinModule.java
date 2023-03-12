package com.narutofood.api.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.narutofood.api.api.model.mixin.CidadeMixin;
import com.narutofood.api.api.model.mixin.CozinhaMixin;
import com.narutofood.api.domain.model.Cidade;
import com.narutofood.api.domain.model.Cozinha;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }


}
