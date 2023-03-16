package com.narutofood.api.api.controller;

import com.narutofood.api.api.assembler.ProdutoDtoAssembler;
import com.narutofood.api.api.assembler.ProdutoInputDisassembler;
import com.narutofood.api.api.model.dto.ProdutoDTO;
import com.narutofood.api.api.model.input.ProdutoInput;
import com.narutofood.api.domain.model.Produto;
import com.narutofood.api.domain.model.Restaurante;
import com.narutofood.api.domain.repository.ProdutoRepository;
import com.narutofood.api.domain.service.CadastroProdutoService;
import com.narutofood.api.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoDtoAssembler assembler;

    @Autowired
    private ProdutoInputDisassembler disassembler;

    @GetMapping
    public List<ProdutoDTO> findAll(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(id);

        List<Produto> todosProdutos = produtoRepository.findByRestaurante(restaurante);

        return assembler.toCollectionModel(todosProdutos);
    }

    @GetMapping("/{id}")
    public ProdutoDTO findById(@PathVariable Long restauranteId, @PathVariable Long id) {
        Produto produto = cadastroProduto.findOrFail(restauranteId, id);

        return assembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO create(@PathVariable Long restauranteId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);

        Produto produto = disassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.save(produto);

        return assembler.toModel(produto);
    }

    @PutMapping("/{id}")
    public ProdutoDTO update(@PathVariable Long restauranteId, @PathVariable Long id,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.findOrFail(restauranteId, id);

        disassembler.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = cadastroProduto.save(produtoAtual);

        return assembler.toModel(produtoAtual);
    }
}   