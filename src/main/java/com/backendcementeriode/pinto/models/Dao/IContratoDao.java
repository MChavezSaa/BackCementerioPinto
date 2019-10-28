package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContratoDao extends JpaRepository<Contrato, Long> {
}
