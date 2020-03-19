package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Difunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDifuntoDao extends JpaRepository<Difunto, Long> {


    @Query(value = "SELECT p FROM Difunto as p WHERE p.id_Difunto not in (select c.difunto.id_Difunto from tumba_difunto as c )")
    public List<Object> difuntosNotIn();


}
