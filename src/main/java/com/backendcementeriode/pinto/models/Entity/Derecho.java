package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "Derecho")
public class Derecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Derecho;


    private LocalDate fecha_Inscripcion_Derecho;


    private LocalDate fecha_Pago_Derecho;


    private LocalDate fecha_Vencimiento_Derecho;

    private float valor_Cuota_Derecho;
    private int numero_Cuotas_Derecho;
    private boolean estadoDerecho;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Contrato contrato;

    private String medioPago_Derecho;


}
