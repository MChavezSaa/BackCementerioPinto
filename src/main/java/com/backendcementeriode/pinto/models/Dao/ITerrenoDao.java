package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Terreno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITerrenoDao extends JpaRepository<Terreno, Long> {

}
