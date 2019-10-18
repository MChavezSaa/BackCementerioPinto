package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
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
    private Date fecha_Nacimiento_Difunto;
    private Date fecha_Defuncion;
    private Date fecha_Inscripcion_Difunto;
    private String nombreC_Padre;
    private String nombreC_Madre;

    private boolean sacramento1;
    private boolean sacramento2;
    private boolean sacramento3;
    private boolean sacramento4;

    private String certificado_Defuncion;
    private String fotocopia_Carnet;

    private boolean estadoDifunto;

    @OneToMany(mappedBy = "difunto")
    Set<Tumba_Difunto> tumba_difunto;
}
