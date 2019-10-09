package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Tumba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tumba;

   //front funciona con un option que trae valores numericos al rescatar el campo
    private int tipo_Tumba;
    private int valor_Tumba;
    private int estado_Tumba;
    private int orientacion_Tumba;
    private int largo;
    private int ancho;



    @ManyToOne
    @JoinColumn(name = "id_Cliente")
    private Cliente cliente;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Funcionario"),
            name = "id_Funcionario", referencedColumnName = "id_Funcionario")
    private Funcionario funcionario;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Patio"),
            name = "id_Patio", referencedColumnName = "id_Patio")
    private Patio patio;


    @OneToMany(mappedBy = "tumba")
    Set<Tumba_Difunto> tumba_difunto;

}
