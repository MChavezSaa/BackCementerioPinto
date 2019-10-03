package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Derecho;
import com.backendcementeriode.pinto.models.Entity.Patio;

import java.util.List;
import java.util.Optional;

public interface IPatioService {

    List<Patio> findAll();
    Derecho save(Patio derecho);
    Optional<Patio> findOne(long id);
    //void delete(Patio patio);
    //void deletebyID(long id);
    Patio findById(long id);

}
