package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Contrato implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_contrato;
    //atributos para armar contrato, pago derecho y pago mantencion


    private LocalDate fecha_Ingreso_Venta;


    private LocalDate fecha_Pago;

    private String medio_Pago;

    private int valor_Terreno;

    private int pagoInicial;

    @Column(name = "numero_cuotas")
    private int n_Cuotas;

    private float VCuotas;

    private boolean estado_Contrato;


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

   /*
   *  @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Tumba"),
            name = "id_Tumba", referencedColumnName = "id_Tumba")
    private List<Tumba> tumba;
   * */


    private String tumba ;

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
