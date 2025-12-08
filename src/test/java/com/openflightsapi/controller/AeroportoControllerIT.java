package com.openflightsapi.controller;

import com.openflightsapi.model.Aeroporto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AeroportoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void deveListarAeroportosRetornandoStatus200() {
        String url = "http://localhost:" + port + "/api/v1/aeroportos";
        ResponseEntity<Aeroporto[]> resposta = restTemplate.getForEntity(url, Aeroporto[].class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());

        Assertions.assertNotNull(resposta.getBody());
        Assertions.assertTrue(resposta.getBody().length > 0, "A lista de aeroportos não deveria estar vazia");
    }

    @Test
    void deveBuscarAeroportoPorIataCorretamente() {
        String url = "http://localhost:" + port + "/api/v1/aeroportos/iata/BR";
        ResponseEntity<Aeroporto> resposta = restTemplate.getForEntity(url, Aeroporto.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());
        Assertions.assertEquals("Goroka Airport", resposta.getBody().getNomeAeroporto());
    }

    @Test
    void deveRetornar404ParaAeroportoInexistente() {
        String url = "http://localhost:" + port + "/api/v1/aeroportos/iata/XYZ999";
        ResponseEntity<Aeroporto> resposta = restTemplate.getForEntity(url, Aeroporto.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }
}