package com.openflightsapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aeroporto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aeroporto {

    @Id
    @Column(name = "id_aeroporto")
    private Integer idAeroporto;

    @Column(name = "nome_aeroporto", nullable = false, length = 500)
    private String nomeAeroporto;

    @Column(name = "codigo_iata", unique = true, length = 3, nullable = false)
    private String codigoIata;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "codigo_pais_iso", length = 2)
    private String codigoPaisIso;

    private Double latitude;
    private Double longitude;
    private Double altitude;
}