package com.narutofood.domain.service;

import com.narutofood.domain.exception.NegocioException;
import com.narutofood.domain.model.Pedido;
import com.narutofood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
public class FluxoPedidoServidce {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirm(Long id) {
        Pedido pedido = emissaoPedidoService.findOrFail(id);

        if (pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s", pedido.getId(), pedido.getStatus(),
                            StatusPedido.CONFIRMADO ));
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());

    }


}
