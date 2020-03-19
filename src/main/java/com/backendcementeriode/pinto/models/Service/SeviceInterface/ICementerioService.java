package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Cementerio;

import java.util.List;
import java.util.Optional;

public interface ICementerioService {
    List<Cementerio> findAll();
    Cementerio save(Cementerio cementerio);
    Optional<Cementerio> findOne(long id);
    Cementerio findById(long id);
    Cementerio forceFind(long id);
}
