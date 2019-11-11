package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Derecho;
import com.backendcementeriode.pinto.models.Entity.Tumba;

import java.util.List;
import java.util.Optional;

public interface ITumbaService {

    List<Tumba> findAll();
    Tumba save(Tumba tumba);
    Optional<Tumba> findOne(long id);
   // void delete(Derecho derecho);
   // void deletebyID(long id);
    Tumba findById(long id);
    List<Tumba>findFreetumbs();

}
