package com.openflightsapi.service;

import com.openflightsapi.exception.AeroportoNaoEncontradoException;
import com.openflightsapi.model.Aeroporto;
import com.openflightsapi.repository.AeroportoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional; // <--- Importante

class AeroportoServiceTest {

    private AeroportoRepository repository;
    private AeroportoService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(AeroportoRepository.class);
        service = new AeroportoService(repository);
    }

    @Test
    void deveConverterPesParaMetrosCorretamente() {
        double resultado = service.converterPesParaMetros(1000.0);
        Assertions.assertEquals(304.8, resultado, 0.01);
    }

    @Test
    void deveConverterNomeDoPaisParaIsoBrasil(){
        String iso = service.obterIsoPais("Brasil");
        Assertions.assertEquals("BR", iso);
    }

    @Test
    void deveConverterNomeDoPaisParaIsoEnglish(){
        String iso = service.obterIsoPais("Brazil");
        Assertions.assertEquals("BR", iso);
    }

    @Test
    void deveLancarExcecaoQuandoNaoAcharIata() {
        Mockito.when(repository.findByCodigoIata("ZZZ")).thenReturn(Optional.empty());

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

    @Test
    void deveRejeitarIataComTamanhoInvalido() {
        Aeroporto aeroporto = new Aeroporto();
        aeroporto.setCodigoIata("ABCD");
        aeroporto.setAltitude(100.0);

        IllegalArgumentException erro = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.salvar(aeroporto);
        });

        Assertions.assertEquals("Código IATA deve ter exatamente 3 letras", erro.getMessage());
    }

}