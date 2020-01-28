package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "CuotasMantencion")
public class CuotasMantencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Cuotas_Mantencion;


    private LocalDate fecha_Pago_CM;


    private LocalDate fecha_Vencimiento_CM;


    private int numero_Cuotas_CM;
    private int valor_Cuota_CM;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Cliente")
    private Cliente cliente;
}
