package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PagosMantencion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_PagosMantencion;

    private Date fechaPago_Mantencion;
    private Date fechaVencimiento_Mantencion;
    private int valorCuota_Mantencion;
    private boolean estadoCuota_Mantencion;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Cuotas_Mantencion"),
            name = "id_Cuotas_Mantencion", referencedColumnName = "id_Cuotas_Mantencion")
    private CuotasMantencion cuotasMantencion;

}

