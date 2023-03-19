package com.narutofood.api.controller;

import com.narutofood.api.assembler.PedidoDTOAssembler;
import com.narutofood.api.assembler.PedidoResumoDTOAssembler;
import com.narutofood.api.model.dto.PedidoDTO;
import com.narutofood.api.model.dto.PedidoResumoDTO;
import com.narutofood.domain.model.Pedido;
import com.narutofood.domain.repository.PedidoRepository;
import com.narutofood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoDTOAssembler assembler;

    @Autowired
    private PedidoResumoDTOAssembler resumoAssembler;

    @GetMapping
    public List<PedidoResumoDTO> listar() {
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        return resumoAssembler.toCollectionModel(todosPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId) {
        Pedido pedido = emissaoPedido.findOrFail(pedidoId);

        return assembler.toModel(pedido);
    }
}