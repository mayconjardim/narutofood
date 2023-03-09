package com.narutofood.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

   @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
    }

    @LocalServerPort
    private int port;
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
    public void deveConter3Cozinhas_QuandoConsultarCozinhas() {

        RestAssured.given()
                .basePath("/cozinhas")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
               // .body("", Matchers.hasSize(3))
                .body("nome", Matchers.hasItems("Italiana"));

    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        RestAssured.given()
                .body("{ \"nome\": \"Italiana\" }")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
