package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.PagosMantencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPagosMantencionDao extends JpaRepository<PagosMantencion, Long> {
    @Query(value = "SELECT * FROM pagos_mantencion as p WHERE p.id_cuotas_mantencion in (select c.id_cuotas_mantencion from cuotas_mantencion as c where c.id_cuotas_mantencion = p.id_cuotas_mantencion in (SELECT a.id_cliente FROM cliente as a WHERE a.id_cliente = c.id_cliente IN(SELECT s.id_cliente FROM contrato as s WHERE s.id_cliente =?1)) )", nativeQuery = true)
    public List<PagosMantencion> cuotasPorIdClienteEnContrato(long id);
}
