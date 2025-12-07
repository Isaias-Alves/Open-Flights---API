package com.openflightsapi.repository;

import com.openflightsapi.model.Aeroporto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AeroportoRepository extends JpaRepository<Aeroporto, Integer> {

    Optional<Aeroporto> findByCodigoIata(String codigoIata);

    boolean existsByCodigoIata(String codigoIata);

    void deleteByCodigoIata(String codigoIata);
}