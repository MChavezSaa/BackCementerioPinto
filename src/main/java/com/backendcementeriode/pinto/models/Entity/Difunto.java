package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "Difunto")
public class Difunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Difunto;

    private String rut_Difunto;
    private String nombres_Difunto;
    private String apellidoP_Difunto;
    private String apellidoM_Difunto;
    private String genero_Difunto;


    private LocalDate fecha_Nacimiento_Difunto;


    private LocalDate fecha_Defuncion;

    private LocalDate fecha_Inscripcion_Difunto;

    private LocalDate fecha_Entierro;

    private String nombreC_Padre;
    private String nombreC_Madre;

    private boolean sacramento1;
    private boolean sacramento2;
    private boolean sacramento3;
    private boolean sacramento4;

    private boolean certificado_Defuncion;
    private boolean fotocopia_Carnet;

    private String estadoDifunto;
}
