package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.Tumba_Difunto;

import java.util.List;
import java.util.Optional;

public interface ITumbaDifuntoService {

    List<Tumba_Difunto> findAll();
    Tumba_Difunto save(Tumba_Difunto difunto);
    Optional<Tumba_Difunto> findOne(long id);
   // void delete(Tumba_Difunto difunto);
   // void deletebyID(long id);
    Optional<Tumba_Difunto> findById(long id);
    Tumba_Difunto contratoPorDifunto(long id);
    List<Object>ListaValidacionTraslado(String idTumba);

}
