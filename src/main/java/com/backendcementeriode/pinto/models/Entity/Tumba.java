package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Tumba")
public class Tumba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tumba;



    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_occiso"),
            name = "id_occiso", referencedColumnName = "id_occiso")
    private Occiso id_occiso;*/

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "id_terreno"),
            name = "id_terreno", referencedColumnName = "id_terreno")
    private Terreno id_terreno;

    private String estado;//puede  ser ocupado o libre
}
