package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class PagosMantencion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_PagosMantencion;


    private LocalDate fechaPago_Mantencion;


    private LocalDate fechaVencimiento_Mantencion;


    private int valorCuota_Mantencion;
    private boolean estadoCuota_Mantencion;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Cuotas_Mantencion"),
            name = "id_Cuotas_Mantencion", referencedColumnName = "id_Cuotas_Mantencion")
    public CuotasMantencion cuotasMantencion;

}

