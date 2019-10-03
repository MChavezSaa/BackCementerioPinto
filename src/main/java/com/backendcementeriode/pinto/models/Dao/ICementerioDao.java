package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Cementerio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICementerioDao extends JpaRepository<Cementerio, Long> {

}
