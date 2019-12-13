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

    private String nombre_Solicitante;
    private String rut_Solicitante;
    private String domicilio_Solicitante;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Funcionario"),
            name = "id_Funcionario", referencedColumnName = "id_Funcionario")
    private Funcionario funcionario;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Difunto"),
            name = "id_Difunto", referencedColumnName = "id_Difunto")
    private Difunto difunto;

    private String lugar_Traslado;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha_Traslado;





}
