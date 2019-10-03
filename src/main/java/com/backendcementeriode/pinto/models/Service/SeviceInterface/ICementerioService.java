package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Cementerio;
import com.backendcementeriode.pinto.models.Entity.Derecho;

import java.util.List;
import java.util.Optional;

public interface ICementerioService {
    List<Cementerio> findAll();
    Derecho save(Cementerio cuotasMantencion);
    Optional<Cementerio> findOne(long id);
    //void delete(Cementerio cementerio);
    //void deletebyID(long id);
    Cementerio findById(long id);
}
