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

    private String nombre_Terreno;
    private int capacidad_Terreno;
    private boolean estado_Terreno;//lleno o ocupable...

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Cementerio"),
            name = "id_Cementerio", referencedColumnName = "id_Cementerio")
    private Cementerio cementerio;


}
