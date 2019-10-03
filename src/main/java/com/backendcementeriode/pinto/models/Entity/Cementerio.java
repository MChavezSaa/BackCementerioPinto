package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Cementerio")
public class Cementerio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Cementerio;
    private int capacidad_Terrenos;

    private String nombre_Cementerio;
    private String direccion_Cementerio;

    private int telefono_Cementerio;

}
