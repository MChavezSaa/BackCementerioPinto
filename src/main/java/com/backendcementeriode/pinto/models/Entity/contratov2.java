package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;

@Data
@Entity
@Table(name = "contratov2")
public class contratov2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_contrato2;

    private LocalDate fecha_Ingreso_Venta;

    private LocalDate fecha_Pago;

    private String medio_Pago;

    private int valor_Terreno;

    private int pagoInicial;

    @Column(name = "numero_cuotas")
    private int n_Cuotas;

    private float VCuotas;


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
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Tumba"),
            name = "id_Tumba", referencedColumnName = "id_Tumba")
    private List<Tumba> tumba;
  */


    private String tumba ;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_TipoTumba"))
    private TipoTumba tipoTumba;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Cliente"))
    private Cliente cliente;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Funcionario"))
    private Funcionario funcionario;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Derecho"))
    private Derecho derecho;


}
