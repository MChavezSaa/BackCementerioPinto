package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.Tumba_Difunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITumbaDifuntoDao extends JpaRepository<Tumba_Difunto, Long> {

    @Query(value = "select h from tumba_difunto h where h.contrato.id_contrato = ?1")
     Tumba_Difunto contratoPorDifunto(long id);

    @Query(value = "select h from tumba_difunto h where h.tumba = ?1 and h.difunto.estadoDifunto='Activo'")
    List<Tumba_Difunto> ListaValidacionTraslado(String tumbaID);
}
