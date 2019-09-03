package com.backendcementeriode.pinto.models.Service;

import com.backendcementeriode.pinto.models.Entity.Funcionario;

import java.util.List;
import java.util.Optional;

public interface IFuncionarioService {
     List<Funcionario> findAll();
     Funcionario save(Funcionario funcionario);
     Optional<Funcionario> findOne(long id);
     void delete(Funcionario funcionario);
     void deletebyID(long id);
     Funcionario findById(long id);

}

