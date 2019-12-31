package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "traslado")
public class Traslado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Traslado;

    private String nombreC_Solicitante;
    private String rut_Solicitante;
    private String direccion_Solicitante;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha_Traslado;


    private String tipoDeCambio;//interno-externo
    private String lugarviejo;
    private String lugarnuevo;

    private String observaciones;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Difunto"),
            name = "id_Difunto", referencedColumnName = "id_Difunto")
    private Difunto difunto;







}
