package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Derecho;

import java.util.List;
import java.util.Optional;

public interface IDerechoService {
    List<Derecho> findAll();
    Derecho save(Derecho derecho);
    Optional<Derecho> findOne(long id);
    void delete(Derecho derecho);
    void deletebyID(long id);
    Derecho findById(long id);
}
