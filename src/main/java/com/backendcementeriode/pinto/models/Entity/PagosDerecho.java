package com.backendcementeriode.pinto.models.Entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PagosDerecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_PagosDerecho;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaPago_Derecho;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaVencimiento_Derecho;

    private float valorCuota_Derecho;
    private boolean estadoCuota_Derecho;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Derecho")
    private Derecho derecho;
}
