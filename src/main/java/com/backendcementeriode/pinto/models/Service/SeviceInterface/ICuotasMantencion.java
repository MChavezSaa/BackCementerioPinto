package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.CuotasMantencion;
import com.backendcementeriode.pinto.models.Entity.Derecho;

import java.util.List;
import java.util.Optional;

public interface ICuotasMantencion {
    List<CuotasMantencion> findAll();
    Derecho save(CuotasMantencion cuotasMantencion);
    Optional<CuotasMantencion> findOne(long id);
    //void delete(CuotasMantencion cuotasMantencion);
    //void deletebyID(long id);
    CuotasMantencion findById(long id);
}
