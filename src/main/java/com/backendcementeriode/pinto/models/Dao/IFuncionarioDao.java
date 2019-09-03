package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFuncionarioDao extends JpaRepository<Funcionario, Long> {
}
