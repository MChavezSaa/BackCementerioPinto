package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class TipoTumba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_TipoTumba;

    private String nombre;
    private int capacidad;
    private boolean estado_TipoTumba;

}
