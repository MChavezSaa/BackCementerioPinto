package com.backendcementeriode.pinto.models.Entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class PagosDerecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_PagosDerecho;


    private LocalDate fechaPago_Derecho;


    private LocalDate fechaVencimiento_Derecho;

    private float valorCuota_Derecho;
    private boolean estadoCuota_Derecho;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Derecho")
    private Derecho derecho;
}
