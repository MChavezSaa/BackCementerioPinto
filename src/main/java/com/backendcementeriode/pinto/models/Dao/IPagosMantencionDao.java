package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.PagosMantencion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagosMantencionDao extends JpaRepository<PagosMantencion, Long> {
}
