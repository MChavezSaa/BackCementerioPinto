package com.backendcementeriode.pinto.models.Dao;

import com.backendcementeriode.pinto.models.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteDao extends JpaRepository<Cliente, Long> {

}
