package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity(name = "Tumba_Difunto")
public class Tumba_Difunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Tumba_Difunto;

    @ManyToOne
    @JoinColumn(name = "id_Difunto")
    Difunto difunto;

    String tumba;
//lalala
    @ManyToOne
    @JoinColumn(name = "id_Contrato")
    Contrato contrato;

    private LocalDate fecha_Entierro_TD;

}
