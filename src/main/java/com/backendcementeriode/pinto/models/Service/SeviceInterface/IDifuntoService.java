package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Derecho;
import com.backendcementeriode.pinto.models.Entity.Difunto;

import java.util.List;
import java.util.Optional;

public interface IDifuntoService {
    List<Difunto> findAll();
    Derecho save(Difunto difunto);
    Optional<Difunto> findOne(long id);
    void delete(Difunto difunto);//deshabilitar difunto
    void deletebyID(long id);//deshabilitar difunto
    Difunto findById(long id);
}
