package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Cementerio;
import com.backendcementeriode.pinto.models.Entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IFuncionarioDao extends JpaRepository<Funcionario, Long> {

    @Query("select u from Funcionario u where u.rut_Funcionario=?1")
    public Funcionario findByRut(String rut);
}
