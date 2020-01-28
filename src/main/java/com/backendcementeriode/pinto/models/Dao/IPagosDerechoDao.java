package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.PagosDerecho;
import com.backendcementeriode.pinto.models.Entity.PagosMantencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPagosDerechoDao extends JpaRepository<PagosDerecho, Long> {


    @Query("select c from PagosDerecho c where c.derecho.id_Derecho = ?1")
    List<PagosDerecho> findByDerecho(long id);

    @Query(value = "SELECT * FROM pagos_derecho as p WHERE p.id_derecho in (select c.id_derecho from derecho as c where c.contrato_id_contrato =?1)", nativeQuery = true)
    public List<PagosDerecho> cuotasPorIdContrato(long id);

}
