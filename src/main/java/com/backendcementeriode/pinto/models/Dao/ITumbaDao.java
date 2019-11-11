package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Tumba;
import com.backendcementeriode.pinto.models.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITumbaDao extends JpaRepository<Tumba, Long> {
    @Query("select t from Tumba t where t.estado_Tumba=false")
    public List<Tumba> findFreetumbs();
}
