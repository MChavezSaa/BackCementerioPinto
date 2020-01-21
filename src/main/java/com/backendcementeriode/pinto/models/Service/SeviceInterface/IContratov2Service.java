package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.contratov2;

import java.util.List;
import java.util.Optional;

public interface IContratov2Service {
    List<contratov2> findAll();
    contratov2 save(contratov2 contrato);
    Optional<contratov2> findOne(long id);

}

