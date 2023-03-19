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
import com.narutofood.domain.service.FluxoPedidoServidce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos/{id}")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoServidce fluxoPedidoServidce;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
   public void confirmar(@PathVariable Long id) {
    fluxoPedidoServidce.confirm(id);
   }

}