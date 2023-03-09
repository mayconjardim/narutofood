package com.narutofood.api;

import com.narutofood.api.domain.exception.CozinhaNaoEncontradaException;
import com.narutofood.api.domain.exception.EntidadeEmUsoException;
import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CadastroCozinhaIT {




}
