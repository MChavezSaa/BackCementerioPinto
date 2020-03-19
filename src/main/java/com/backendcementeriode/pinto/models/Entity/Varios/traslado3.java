package com.backendcementeriode.pinto.models.Entity.Varios;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.Difunto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
public class traslado3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Traslado;

    private String nombreC_Solicitante;
    private String rut_Solicitante;
    private String direccion_Solicitante;
    private LocalDate fecha_Traslado;
    private String tipoDeCambio;
    private String lugarviejo;
    private Contrato lugarnuevo;
    private String observaciones;
    private Difunto difunto;

}
