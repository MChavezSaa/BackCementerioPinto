package com.backendcementeriode.pinto.models.Service;

import com.backendcementeriode.pinto.models.Entity.Occiso;

import java.util.List;
import java.util.Optional;

public interface IOccisoService {

    List<Occiso> findAll();
    Occiso save(Occiso funcionario);
    Optional<Occiso> findOne(long id);
    void delete(Occiso funcionario);
    void deletebyID(long id);
    Occiso findById(long id);



}
