package com.openflightsapi.config;

import com.openflightsapi.model.Aeroporto;
import com.openflightsapi.repository.AeroportoRepository;
import com.openflightsapi.model.util.Conversor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Configuration
public class CargaInicial {

    @Bean
    CommandLineRunner carregarDados(AeroportoRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                System.out.println("Banco já populado. Pulando carga inicial.");
                return;
            }

            System.out.println("Iniciando carga de dados do CSV...");

            try (InputStream is = getClass().getResourceAsStream("/airports (1).csv");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(";");

                    try {
                        Aeroporto a = new Aeroporto();
                        a.setIdAeroporto(Integer.parseInt(dados[0]));
                        a.setNomeAeroporto(dados[1]);
                        a.setCidade(dados[2]);

                        String iso = Conversor.obterIsoPais(dados[3]);
                        a.setCodigoPaisIso(iso);

                        a.setCodigoIata(dados[4]);
                        a.setLatitude(Double.parseDouble(dados[6]));
                        a.setLongitude(Double.parseDouble(dados[7]));
                        a.setAltitude(Double.parseDouble(dados[8]));

                        repository.save(a);
                    } catch (Exception e) {
                        System.out.println("Erro na linha: " + linha + " -> " + e.getMessage());
                    }
                }
                System.out.println("Carga de dados finalizada com sucesso!");
            }
        };
    }
}