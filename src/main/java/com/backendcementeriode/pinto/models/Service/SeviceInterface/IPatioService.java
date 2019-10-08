package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Patio;

import java.util.List;
import java.util.Optional;

public interface IPatioService {

    List<Patio> findAll();
    Patio save(Patio patio);
    Optional<Patio> findOne(long id);
    void delete(Patio patio);
    void deletebyID(long id);
    Optional<Patio> findById(long id);

}
