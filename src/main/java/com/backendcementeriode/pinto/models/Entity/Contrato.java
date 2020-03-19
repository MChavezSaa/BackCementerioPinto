package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
public class Contrato implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_contrato;

    private LocalDate fecha_Ingreso_Venta;


    private LocalDate fecha_Pago;

    private String medio_Pago;

    private int valor_Terreno;

    private int pagoInicial;

    @Column(name = "numero_cuotas")
    private int n_Cuotas;

    private float VCuotas;

    private boolean estado_Contrato;

    @Column(name = "perpetuidad")
    private int perpetuidad;


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


