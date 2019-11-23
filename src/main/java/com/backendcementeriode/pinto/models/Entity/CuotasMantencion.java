package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CuotasMantencion")
public class CuotasMantencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Cuotas_Mantencion;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha_Pago_CM;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha_Vencimiento_CM;


    private int numero_Cuotas_CM;
    private int valor_Cuota_CM;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Cliente")
    private Cliente cliente;
}
