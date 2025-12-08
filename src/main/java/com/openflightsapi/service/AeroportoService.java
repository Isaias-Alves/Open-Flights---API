package com.openflightsapi.service;

import com.openflightsapi.exception.AeroportoNaoEncontradoException;
import com.openflightsapi.model.Aeroporto;
import com.openflightsapi.repository.AeroportoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AeroportoService {

    @Autowired
    private AeroportoRepository repository;

    public List<Aeroporto> listarTodos() {
        return repository.findAll();
    }

    public Aeroporto buscarPorIata(String iata) {
        Optional<Aeroporto> aeroporto = repository.findByCodigoIata(iata);

        if (aeroporto.isEmpty()) {
            throw new AeroportoNaoEncontradoException(iata);
        }

        return aeroporto.orElse(null);
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