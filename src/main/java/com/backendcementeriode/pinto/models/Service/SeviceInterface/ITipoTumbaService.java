package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.TipoTumba;

import java.util.List;

public interface ITipoTumbaService {

    List<TipoTumba> findAll();
    TipoTumba save(TipoTumba tipoTumba);
    void delete(TipoTumba tipoTumba);
    void deletebyID(long id);
    TipoTumba findById(long id);
}
