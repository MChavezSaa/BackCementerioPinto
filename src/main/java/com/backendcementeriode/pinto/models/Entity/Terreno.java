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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Cementerio"),
            name = "id_Cementerio", referencedColumnName = "id_Cementerio")
    private Cementerio cementerio;

}
