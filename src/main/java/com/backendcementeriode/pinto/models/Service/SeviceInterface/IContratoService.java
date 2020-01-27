package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.CuotasMantencion;
import com.backendcementeriode.pinto.models.Entity.PagosMantencion;

import java.util.List;
import java.util.Optional;

public interface IContratoService {
    List<Contrato> findAll();
    Contrato save(Contrato contrato);
    Optional<Contrato> findOne(long id);

}
