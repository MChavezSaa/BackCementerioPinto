package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface IContratoDao extends JpaRepository<Contrato, Long> {

    @Query(value = "select DISTINCT c.cliente from Contrato c")
    public List<Object> distincCliente();

    @Query(value = "select c from Contrato c where c.cliente.rut_Cliente = ?1")
    public List<Contrato> contratosPorUsernameequalRut(String rut);

    @Query(value = "SELECT h from Contrato as h WHERE h.fecha_Ingreso_Venta BETWEEN ?1 and ?2")
    public List<Object> getContratoFechas(LocalDate fechaInicio , LocalDate fechaFin);

}
