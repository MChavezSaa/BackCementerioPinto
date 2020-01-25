package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "contratov2")
public class contratov2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_contrato2;
    //atributos para armar contrato, pago derecho y pago mantencion


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha_Ingreso_Venta;


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha_Pago;

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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Tumba"))
    private Tumba tumba;

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
