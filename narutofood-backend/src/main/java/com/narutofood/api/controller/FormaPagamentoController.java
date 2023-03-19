package com.narutofood.api.controller;

import com.narutofood.api.assembler.FormaPagamentoDtoAssembler;
import com.narutofood.api.assembler.FormaPagamentoInputDisassembler;
import com.narutofood.api.model.dto.FormaPagamentoDTO;
import com.narutofood.api.model.input.FormaPagamentoInput;
import com.narutofood.domain.model.FormaPagamento;
import com.narutofood.domain.repository.FormaPagamentoRepository;
import com.narutofood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private FormaPagamentoDtoAssembler assembler;

    @Autowired
    private FormaPagamentoInputDisassembler disassembler;

    @GetMapping
    public List<FormaPagamentoDTO> findAll() {
        List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();

        return assembler.toCollectionModel(todasFormasPagamentos);
    }

    @GetMapping("/{id}")
    public FormaPagamentoDTO findById(@PathVariable Long id) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.findOrFail(id);

        return assembler.toModel(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = disassembler.toDomainObject(formaPagamentoInput);

        formaPagamento = cadastroFormaPagamento.save(formaPagamento);

        return assembler.toModel(formaPagamento);
    }

    @PutMapping("/{id}")
    public FormaPagamentoDTO update(@PathVariable Long id,
                                         @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.findOrFail(id);

        disassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        formaPagamentoAtual = cadastroFormaPagamento.save(formaPagamentoAtual);

        return assembler.toModel(formaPagamentoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroFormaPagamento.delete(id);
    }
}                