package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Tumba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tumba;

    private int numero_Tumba;
    private int valor_Tumba;
    private String orientacion_Tumba;
    private float largo;
    private float ancho;
    private int nivel;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Patio")
    private Patio patio;

    @ManyToOne(cascade = CascadeType.MERGE)
    private TipoTumba tipo_Tumba;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Cliente")
    private Cliente cliente;

     private String estado_Tumba;




}
