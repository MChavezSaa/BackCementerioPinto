package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Tumba")
public class Tumba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Occiso id_occiso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Terreno id_terreno;

    private String estado;//puede  ser ocupado o libre
}
