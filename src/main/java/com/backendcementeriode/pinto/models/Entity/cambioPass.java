package com.backendcementeriode.pinto.models.Entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class cambioPass {
    private String antigua;
    private String nueva;

}
