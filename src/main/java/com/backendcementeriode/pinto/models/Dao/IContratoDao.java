package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.CuotasMantencion;
import com.backendcementeriode.pinto.models.Entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface IContratoDao extends CrudRepository<Contrato, Long> {

    @Query(value = "SELECT * FROM pagos_mantencion as p WHERE p.id_cuotas_mantencion in (select c.id_cuotas_mantencion from cuotas_mantencion as c where c.id_cuotas_mantencion = p.id_cuotas_mantencion in (SELECT a.id_cliente FROM cliente as a WHERE a.id_cliente = c.id_cliente IN(SELECT s.id_cliente FROM contrato as s WHERE s.id_cliente =?1)) )= ?1", nativeQuery = true)
    public List<CuotasMantencion> cuotasPorIdClienteEnContrato(long id);
   


}
