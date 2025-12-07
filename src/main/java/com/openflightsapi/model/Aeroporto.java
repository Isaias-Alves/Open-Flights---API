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
    private Integer idAeroporto;

    @Column(nullable = false)
    private String nomeAeroporto;

    @Column(unique = true, length = 3, nullable = false)
    private String codigoIata;

    private String cidade;

    @Column(length = 2)
    private String codigoPaisIso;

    private Double latitude;
    private Double longitude;
    private Double altitude;
}