package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Cementerio;
import com.backendcementeriode.pinto.models.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICementerioDao extends JpaRepository<Cementerio, Long> {

    @Query("select u from Cementerio u where u.id_Cementerio=?1")
    public Cementerio forceFind(Long id);
}
