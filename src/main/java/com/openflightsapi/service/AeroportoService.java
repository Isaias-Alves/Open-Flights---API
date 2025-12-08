package com.openflightsapi.service;

import com.openflightsapi.exception.AeroportoNaoEncontradoException;
import com.openflightsapi.model.Aeroporto;
import com.openflightsapi.repository.AeroportoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AeroportoService {

    private final AeroportoRepository repository;

    public AeroportoService(AeroportoRepository repository) {
        this.repository = repository;
    }

    public List<Aeroporto> listarTodos() {
        return repository.findAll();
    }

    public Aeroporto buscarPorIata(String iata) {
        return repository.findByCodigoIata(iata)
                .orElseThrow(() -> new AeroportoNaoEncontradoException(iata));
    }

    public Aeroporto salvar(Aeroporto aeroporto) {
        if (aeroporto.getCodigoIata() == null || aeroporto.getCodigoIata().length() != 3) {
            throw new IllegalArgumentException("Código IATA deve ter exatamente 3 letras");
        }

        if (aeroporto.getAltitude() != null && aeroporto.getAltitude() < 0) {
            throw new IllegalArgumentException("A altitude não pode ser negativa");
        }

        return repository.save(aeroporto);
    }

    @Transactional
    public void deletar(String iata) {
        if (!repository.existsByCodigoIata(iata)) {
            throw new AeroportoNaoEncontradoException(iata);
        }
        repository.deleteByCodigoIata(iata);
    }

    public double converterPesParaMetros(double pes) {
        return pes * 0.3048;
    }


}