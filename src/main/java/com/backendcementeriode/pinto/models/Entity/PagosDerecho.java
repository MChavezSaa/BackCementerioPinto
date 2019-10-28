package com.backendcementeriode.pinto.models.Entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PagosDerecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_PagosDerecho;

    private Date fechaPago_Derecho;
    private Date fechaVencimiento_Derecho;
    private int valorCuota_Derecho;
    private boolean estadoCuota_Derecho;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Derecho")
    private Derecho derecho;
}
