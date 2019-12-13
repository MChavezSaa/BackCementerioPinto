package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Terreno")
public class Terreno {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_terreno;

    private int capacidad_Terreno;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Cementerio")
    private Cementerio cementerio;

    private boolean estado_Terreno;//lleno o ocupable...
    private String nombre_Terreno;




}
