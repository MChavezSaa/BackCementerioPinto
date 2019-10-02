package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Cliente;

    private String rut_Cliente;
    private String nombres_Cliente;
    private String apellidoP_Cliente;
    private String apellidoM_Cliente;
    private int genero_Cliente;
    private int telefono_Cliente;
    private String direccion_Cliente;
    /*----------FAMILIAR---------*/
    private String rut_Familiar;
    private String nombres_Familiar;
    private String apellidoP_Familiar;
    private String apellidoM_Familiar;
    private int telefono_Familiar;

    private boolean estado_Cliente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_Derecho"),
            name = "id_Derecho", referencedColumnName = "id_Derecho")
    private Derecho derecho;


}
