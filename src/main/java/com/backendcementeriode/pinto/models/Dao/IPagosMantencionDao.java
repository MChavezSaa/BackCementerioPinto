package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.PagosMantencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPagosMantencionDao extends JpaRepository<PagosMantencion, Long> {

    @Query(value = "SELECT * FROM pagos_mantencion as p WHERE p.id_cuotas_mantencion in (select c.id_cuotas_mantencion from cuotas_mantencion as c where c.id_contrato =?1)", nativeQuery = true)
    public List<PagosMantencion> cuotasPorIdClienteEnContrato(long id);


}
