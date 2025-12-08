package com.openflightsapi.service;

import com.openflightsapi.exception.AeroportoNaoEncontradoException;
import com.openflightsapi.model.Aeroporto;
import com.openflightsapi.repository.AeroportoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AeroportoServiceTest {

    @Mock
    private AeroportoRepository repository;

    @InjectMocks
    private AeroportoService service;

    @Test
    void deveConverterPesParaMetrosCorretamente() {
        double resultado = service.converterPesParaMetros(1000.0);
        Assertions.assertEquals(304.8, resultado, 0.01);
    }

    @Test
    void deveLancarExcecaoQuandoNaoAcharIata() {
        Mockito.when(repository.findByCodigoIata("ZZZ")).thenReturn(null);

        Assertions.assertThrows(AeroportoNaoEncontradoException.class, () -> {
            service.buscarPorIata("ZZZ");
        });
    }

    @Test
    void deveLancarExcecaoAoTentarDeletarIataInexistente() {
        Mockito.when(repository.existsByCodigoIata("ZZZ")).thenReturn(false);

        Assertions.assertThrows(AeroportoNaoEncontradoException.class, () -> {
            service.deletar("ZZZ");
        });
    }

    @Test
    void deveRejeitarAltitudeNegativa() {
        Aeroporto aeroporto = new Aeroporto();
        aeroporto.setCodigoIata("GRU");
        aeroporto.setAltitude(-100.0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.salvar(aeroporto);
        });
    }
}