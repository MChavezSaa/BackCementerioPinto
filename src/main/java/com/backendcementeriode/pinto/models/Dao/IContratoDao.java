package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface IContratoDao extends JpaRepository<Contrato, Long> {

   // @Query(value = "SELECT * FROM pagos_mantencion as p WHERE p.id_cuotas_mantencion in (select c.id_cuotas_mantencion from cuotas_mantencion as c where c.id_cuotas_mantencion = p.id_cuotas_mantencion in (SELECT a.id_cliente FROM cliente as a WHERE a.id_cliente = c.id_cliente IN(SELECT s.id_cliente FROM contrato as s WHERE s.id_cliente =?1)) )", nativeQuery = true)
  //  public List<PagosMantencion> cuotasPorIdClienteEnContrato(long id);
   
    @Query(value = "select DISTINCT c.cliente from Contrato c")
    public List<Object> distincCliente();

    @Query(value = "select c from Contrato c where c.cliente.rut_Cliente = ?1")
    public List<Contrato> contratosPorUsernameequalRut(String rut);

    @Query(value = "SELECT h from Contrato as h WHERE h.fecha_Ingreso_Venta BETWEEN ?1 and ?2")
    public List<Object> getContratoFechas(LocalDate fechaInicio , LocalDate fechaFin);

}
