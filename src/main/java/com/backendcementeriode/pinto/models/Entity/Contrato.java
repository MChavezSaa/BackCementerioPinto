package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Contrato {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_contrato;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Cementerio"),
            name = "id_Cementerio", referencedColumnName = "id_Cementerio")
    private Cementerio cementerio;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Terreno"),
            name = "id_Terreno", referencedColumnName = "id_Terreno")
    private Terreno terreno;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Patio"),
            name = "id_Patio", referencedColumnName = "id_Patio")
    private Patio patio;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Tumba"),
            name = "id_Tumba", referencedColumnName = "id_Tumba")
    private Tumba tumba;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_TipoTumba"),
            name = "id_TipoTumba", referencedColumnName = "id_TipoTumba")
    private TipoTumba tipoTumba;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Cliente"),
            name = "id_Cliente", referencedColumnName = "id_Cliente")
    private Cliente cliente;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Funcionario"),
            name = "id_Funcionario", referencedColumnName = "id_Funcionario")
    private Funcionario funcionario;


    //atributos para armar contrato, pago derecho y pago mantencion

    private Date fecha_Ingreso_Venta;
    private String medio_Pago;
    private int valor_Terreno;
    private int pagoInicial;
    private int nCuotas;
    private int VCuotas;
    private Date fecha_Pago;

}


/*
  *
   @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Derecho"),
            name = "id_Derecho", referencedColumnName = "id_Derecho")
    private Derecho derecho;
    *
    *
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_PagosDerecho"),
            name = "id_PagosDerecho", referencedColumnName = "id_PagosDerecho")
    private PagosDerecho pagosDerecho;
  * */
