package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.IClienteDao;
import com.backendcementeriode.pinto.models.Entity.Cliente;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {
    @Autowired
    public IClienteDao clienteDao;


    @Transactional
    public List<Cliente> findAll() {
        return clienteDao.findAll();
    }

    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Transactional
    public Optional<Cliente> findOne(long id) {
        return clienteDao.findById(id);
    }

    @Transactional
    public void delete(Cliente cliente) {
        Cliente clienteADesactivar = cliente;
        clienteADesactivar.setEstadoCliente(false);
        clienteDao.save(clienteADesactivar);
    }

    @Transactional
    public void deletebyID(long id) {
        Optional<Cliente> clienteBuscado = clienteDao.findById(id);
        clienteBuscado.get().setEstadoCliente(false);
        clienteDao.save(clienteBuscado.get());
    }

    @Transactional
    public Optional<Cliente> findById(long id) {
        return clienteDao.findById(id);
    }
}
