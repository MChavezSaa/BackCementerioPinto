package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CuotasMantencion")
public class CuotasMantencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Cuotas_Mantencion;

    private Date fecha_Pago_CM;
    private Date fecha_Vencimiento_CM;
    private int numero_Cuotas_CM;
    private int valor_Cuota_CM;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Cliente"),
            name = "id_Cliente", referencedColumnName = "id_Cliente")
    private Cliente cliente;
}
