package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Derecho")
public class Derecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Derecho;

    private Date fecha_Inscripcion_Derecho;
    private Date fecha_Pago_Derecho;
    private Date fecha_Vencimiento_Derecho;
    private int valor_Cuota_Derecho;
    private int numero_Cuotas_Derecho;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Cliente"),
            name = "id_Cliente", referencedColumnName = "id_Cliente")
    private Cliente cliente;


}
