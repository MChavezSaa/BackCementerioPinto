package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Terreno;

import java.util.List;
import java.util.Optional;

public interface ITerrenoService {

    List<Terreno> findAll();
    Terreno save(Terreno terreno);
    Optional<Terreno> findOne(long id);
    void delete(Terreno terreno);
    void deletebyID(long id);
    Terreno findById(long id);

}
