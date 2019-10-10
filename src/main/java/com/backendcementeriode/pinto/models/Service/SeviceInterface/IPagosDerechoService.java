package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.PagosDerecho;
import com.backendcementeriode.pinto.models.Entity.PagosMantencion;

import java.util.List;
import java.util.Optional;

public interface IPagosDerechoService {

    List<PagosDerecho> findAll();
    PagosDerecho save(PagosDerecho pagosDerecho);
    Optional<PagosDerecho> findById(long id);

}
