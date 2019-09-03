package com.backendcementeriode.pinto.models.Service;

import com.backendcementeriode.pinto.models.Entity.Terreno;

import java.util.List;
import java.util.Optional;

public interface ITerrenoService {

    List<Terreno> findAll();
    void save(Terreno terreno);
    Optional<Terreno> findOne(long id);
    void delete(Terreno terreno);

}
