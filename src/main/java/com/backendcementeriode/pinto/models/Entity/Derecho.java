package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Derecho")
public class Derecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Derecho;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha_Inscripcion_Derecho;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha_Pago_Derecho;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha_Vencimiento_Derecho;

    private float valor_Cuota_Derecho;
    private int numero_Cuotas_Derecho;
    private boolean estadoDerecho;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Cliente cliente;

    private String medioPago_Derecho;


}
