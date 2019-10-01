package com.backendcementeriode.pinto.models.Entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Occiso")
public class Occiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Occiso;

    private String rut_occiso;
    private String nombres;
    private String Apellidos;
    private Date Fecha_defuncion;

}
