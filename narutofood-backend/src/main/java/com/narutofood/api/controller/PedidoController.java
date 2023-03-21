package com.narutofood.api.controller;

import com.narutofood.api.assembler.PedidoDTOAssembler;
import com.narutofood.api.assembler.PedidoInputDisassembler;
import com.narutofood.api.assembler.PedidoResumoDTOAssembler;
import com.narutofood.api.model.dto.PedidoDTO;
import com.narutofood.api.model.dto.PedidoResumoDTO;
import com.narutofood.api.model.input.PedidoInput;
import com.narutofood.domain.exception.EntidadeNaoEncontradaException;
import com.narutofood.domain.exception.NegocioException;
import com.narutofood.domain.model.Pedido;
import com.narutofood.domain.model.Usuario;
import com.narutofood.domain.repository.PedidoRepository;
import com.narutofood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    private PedidoInputDisassembler disassembler;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = disassembler.toDomainObject(pedidoInput);

            // TOD  O pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return assembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}