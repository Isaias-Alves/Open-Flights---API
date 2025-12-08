package com.openflightsapi.controller;

import com.openflightsapi.model.Aeroporto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AeroportoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Random random = new Random();


    @Test
    void deveListarAeroportosRetornandoStatus200() {
        String url = "http://localhost:" + port + "/api/v1/aeroportos";
        ResponseEntity<Aeroporto[]> resposta = restTemplate.getForEntity(url, Aeroporto[].class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());
        Assertions.assertTrue(resposta.getBody().length > 0, "A lista não deveria estar vazia se o banco foi populado");
    }

    @Test
    void deveBuscarAeroportoPorIataCorretamente() {
        String url = "http://localhost:" + port + "/api/v1/aeroportos/MAG";
        ResponseEntity<Aeroporto> resposta = restTemplate.getForEntity(url, Aeroporto.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertEquals("Madang Airport", resposta.getBody().getNomeAeroporto());
    }


    @Test
    void deveCriarNovoAeroporto() {
        int idAleatorio = random.nextInt(10000, 99999);
        String iataAleatorio = "T" + random.nextInt(10, 99);

        Aeroporto novo = new Aeroporto();
        novo.setIdAeroporto(idAleatorio);
        novo.setNomeAeroporto("Aeroporto Teste Criação");
        novo.setCodigoIata(iataAleatorio);
        novo.setCidade("Test City");
        novo.setCodigoPaisIso("BR");
        novo.setAltitude(100.0);

        String url = "http://localhost:" + port + "/api/v1/aeroportos";

        ResponseEntity<Aeroporto> resposta = restTemplate.postForEntity(url, novo, Aeroporto.class);

        Assertions.assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());
        Assertions.assertEquals(iataAleatorio, resposta.getBody().getCodigoIata());
    }

    @Test
    void deveAtualizarAeroportoExistente() {
        int idTemp = random.nextInt(10000, 99999);
        String iataTemp = "U" + random.nextInt(10, 99); // Ex: U88

        Aeroporto temp = new Aeroporto();
        temp.setIdAeroporto(idTemp);
        temp.setNomeAeroporto("Original");
        temp.setCodigoIata(iataTemp);
        temp.setAltitude(10.0);

        String urlBase = "http://localhost:" + port + "/api/v1/aeroportos";
        restTemplate.postForLocation(urlBase, temp);

        String urlUpdate = urlBase + "/" + iataTemp;

        Aeroporto paraAtualizar = restTemplate.getForObject(urlUpdate, Aeroporto.class);
        paraAtualizar.setNomeAeroporto("Nome Atualizado");

        HttpEntity<Aeroporto> requestUpdate = new HttpEntity<>(paraAtualizar);
        ResponseEntity<Aeroporto> resposta = restTemplate.exchange(urlUpdate, HttpMethod.PUT, requestUpdate, Aeroporto.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertEquals("Nome Atualizado", resposta.getBody().getNomeAeroporto());
    }

    @Test
    void deveDeletarAeroportoEVerificar404() {
        int idTemp = random.nextInt(10000, 99999);
        String iataTemp = "D" + random.nextInt(10, 99);

        Aeroporto descartavel = new Aeroporto();
        descartavel.setIdAeroporto(idTemp);
        descartavel.setNomeAeroporto("Vai ser deletado");
        descartavel.setCodigoIata(iataTemp);
        descartavel.setAltitude(10.0);

        String urlBase = "http://localhost:" + port + "/api/v1/aeroportos";
        restTemplate.postForLocation(urlBase, descartavel);

        String urlDel = urlBase + "/" + iataTemp;
        restTemplate.delete(urlDel);

        ResponseEntity<Aeroporto> resposta = restTemplate.getForEntity(urlDel, Aeroporto.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }
}