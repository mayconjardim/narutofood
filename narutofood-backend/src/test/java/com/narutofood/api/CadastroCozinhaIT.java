package com.narutofood.api;

import com.narutofood.api.domain.model.Cozinha;
import com.narutofood.api.domain.repository.CozinhaRepository;
import com.narutofood.api.util.DatabaseCleaner;
import com.narutofood.api.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

    private static final int COZINHA_ID_INEXISTENTE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private Cozinha cozinhaAmericana;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaChinesa;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-chinesa.json");

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(quantidadeCozinhasCadastradas));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        RestAssured.given()
                .body(jsonCorretoCozinhaChinesa)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        RestAssured.given()
                .pathParam("cozinhaId", cozinhaAmericana.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        RestAssured.given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        cozinhaRepository.save(cozinhaTailandesa);

        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
    }

}

