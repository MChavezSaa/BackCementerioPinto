package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Cementerio;
import com.backendcementeriode.pinto.models.Entity.Tumba;

import java.util.List;
import java.util.Optional;

public interface ICementerioService {
    List<Cementerio> findAll();
    Cementerio save(Cementerio cementerio);
    Optional<Cementerio> findOne(long id);
    //void delete(Cementerio cementerio);
    //void deletebyID(long id);
    Cementerio findById(long id);
    Cementerio forceFind(long id);
}
