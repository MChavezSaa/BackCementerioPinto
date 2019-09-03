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
    //tumbas seria la cantidad de patios pero para probar rapido lo dejare asi
    //luego hay que crear el de patio que contenga las tumbas
    private int tumbas;
}
