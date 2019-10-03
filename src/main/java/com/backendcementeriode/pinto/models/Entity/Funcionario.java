package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_funcionario;

    private String rut_Funcionario;
    private String nombres_Funcionario;
    private String apellidoP_Funcionario;
    private String apellidoM_Funcionario;
    private String cargo_Funcionario;
    private boolean estado_funcionario;

}
