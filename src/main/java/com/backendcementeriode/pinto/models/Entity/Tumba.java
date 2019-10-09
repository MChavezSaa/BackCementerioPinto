package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Tumba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tumba;

   //front funciona con un option que trae valores numericos al rescatar el campo
     private int capacidad_Tumba;
     private int valor_Tumba;
     private boolean estado_Disponible;//cumplio o no su capacidad TRUE para 100% ocupada y false aun pueden enterrar difuntos
     private int estado_Tumba;//0: Disponible -1: Vendido-sinOcupar -2: Vendido-Ocupado
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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_TipoTumba"),
         name = "id_TipoTumba", referencedColumnName = "id_TipoTumba")
    private TipoTumba tipo_Tumba;

}
