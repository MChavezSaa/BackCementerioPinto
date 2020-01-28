package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.PagosMantencion;

import java.util.List;
import java.util.Optional;

public interface IPagosMantencionService {

    List<PagosMantencion> findAll();
    PagosMantencion save(PagosMantencion pagosMantencion);
    Optional<PagosMantencion> findById(long id);
    List<PagosMantencion> cuotasPorIdClienteEnContrato(long id);


}
