package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Funcionario")
public class Funcionario {
//para hacer commit
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_funcionario;

    private String nombre;
    private String apellido ;
    private String cargo;



}
