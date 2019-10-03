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
        Cliente clienteADesactivar = null;
        if (clienteBuscado != null) {
            //datos cliente
        clienteADesactivar.setId_Cliente(clienteBuscado.get().getId_Cliente());
        clienteADesactivar.setNombres_Cliente(clienteBuscado.get().getNombres_Cliente());
        clienteADesactivar.setApellidoP_Cliente(clienteBuscado.get().getApellidoP_Cliente());
        clienteADesactivar.setApellidoM_Cliente(clienteBuscado.get().getApellidoM_Cliente());
        clienteADesactivar.setGenero_Cliente(clienteBuscado.get().getGenero_Cliente());
        clienteADesactivar.setRut_Cliente(clienteBuscado.get().getRut_Cliente());
        clienteADesactivar.setTelefono_Cliente(clienteBuscado.get().getTelefono_Cliente());
        clienteADesactivar.setDireccion_Cliente(clienteBuscado.get().getDireccion_Cliente());
        clienteADesactivar.setEstadoCliente(false);
        clienteADesactivar.setDerecho(clienteBuscado.get().getDerecho());
        //datos familiar cliente
        clienteADesactivar.setNombres_Familiar(clienteBuscado.get().getNombres_Familiar());
        clienteADesactivar.setApellidoP_Cliente(clienteBuscado.get().getApellidoP_Cliente());
        clienteADesactivar.setApellidoM_Cliente(clienteBuscado.get().getApellidoM_Cliente());
        clienteADesactivar.setTelefono_Familiar(clienteBuscado.get().getTelefono_Familiar());
        clienteADesactivar.setRut_Familiar(clienteBuscado.get().getRut_Familiar());

        clienteDao.save(clienteADesactivar);
        }
    }

    @Transactional
    public Optional<Cliente> findById(long id) {
        return clienteDao.findById(id);
    }
}
