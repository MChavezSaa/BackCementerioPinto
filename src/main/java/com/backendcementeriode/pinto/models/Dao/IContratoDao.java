package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Contrato;
import org.springframework.data.repository.CrudRepository;

public interface IContratoDao extends CrudRepository<Contrato, Long> {
}
