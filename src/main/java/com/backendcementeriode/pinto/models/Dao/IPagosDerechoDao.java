package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.PagosDerecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPagosDerechoDao extends JpaRepository<PagosDerecho, Long> {


    @Query("select c from PagosDerecho c where c.Derecho.id_Derecho = ?1")
    List<PagosDerecho> findByDerecho(long id);

}
