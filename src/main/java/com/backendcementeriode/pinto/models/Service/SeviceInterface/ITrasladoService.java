package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Traslado;
import com.backendcementeriode.pinto.models.Entity.Tumba_Difunto;

import java.util.List;
import java.util.Optional;

public interface ITrasladoService {
    List<Traslado> findAll();
    Traslado save(Traslado traslado);
    Traslado findOne(long id);


}
