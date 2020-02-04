package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Tumba_Difunto")
public class Tumba_Difunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Tumba_Difunto;

    @ManyToOne
    @JoinColumn(name = "id_Difunto")
    Difunto difunto;

    @ManyToOne
    @JoinColumn(name = "id_Tumba")
    Tumba tumba;

    @ManyToOne
    @JoinColumn(name = "id_Contrato")
    Contrato contrato;

    private Date fecha_Entierro_TD;

}
