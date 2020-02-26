package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Patio")
public class Patio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Patio;

    private int capacidad_Patio;
    private String nombre_Patio;
    private boolean estado_Patio;
    private  String nombreTT;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Terreno")
    private Terreno terreno;


}
