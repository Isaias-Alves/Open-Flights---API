package com.openflightsapi.controller;

import com.openflightsapi.model.Aeroporto;
import com.openflightsapi.service.AeroportoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aeroportos")
public class AeroportoController {

    @Autowired
    private AeroportoService service;

    @GetMapping
    public ResponseEntity<List<Aeroporto>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/iata/{iata}")
    public ResponseEntity<Aeroporto> buscarPorIata(@PathVariable String iata) {
        try {
            return ResponseEntity.ok(service.buscarPorIata(iata));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Aeroporto> criar(@RequestBody Aeroporto aeroporto) {
        try {
            Aeroporto novo = service.salvar(aeroporto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/iata/{iata}")
    public ResponseEntity<Aeroporto> atualizar(@PathVariable String iata, @RequestBody Aeroporto aeroporto) {
        try {
            aeroporto.setCodigoIata(iata);
            return ResponseEntity.ok(service.salvar(aeroporto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/iata/{iata}")
    public ResponseEntity<Void> deletar(@PathVariable String iata) {
        try {
            service.deletar(iata);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}