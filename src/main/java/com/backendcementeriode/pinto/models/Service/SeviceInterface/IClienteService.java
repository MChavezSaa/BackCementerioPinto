package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    List<Cliente> findAll();
    Cliente save(Cliente cliente);
    Optional<Cliente> findOne(long id);
    void delete(Cliente cliente);
    void deletebyID(long id);
    Optional<Cliente> findById(long id);
}
