package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Traslado;

import java.util.List;

public interface ITrasladoService {
    List<Traslado> findAll();
    Traslado save(Traslado traslado);
    Traslado findOne(long id);


}
