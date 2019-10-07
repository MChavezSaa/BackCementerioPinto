package com.backendcementeriode.pinto.models.Service.classImpl;


import com.backendcementeriode.pinto.models.Dao.IFuncionarioDao;
import com.backendcementeriode.pinto.models.Entity.Derecho;
import com.backendcementeriode.pinto.models.Entity.Funcionario;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IFuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public  class FuncionarioServiceImpl  implements IFuncionarioService {

    @Autowired
    public IFuncionarioDao funcionarioDao;


    @Transactional
    public List<Funcionario> findAll() {

        return funcionarioDao.findAll();
    }

    @Transactional
    public Funcionario save(Funcionario funcionario) {

        return funcionarioDao.save(funcionario);
    }

    @Transactional
    public Optional<Funcionario> findOne(long id) {

        return funcionarioDao.findById(id);
    }

    @Transactional
    public void delete(Funcionario funcionario) {

        Funcionario funcionarioADesactivar = funcionario;
        funcionarioADesactivar.setEstado_funcionario(false);
        funcionarioDao.save(funcionarioADesactivar);
    }

    @Override
    public void deletebyID(long id) {
        Optional<Funcionario> funcionarioBuscado = funcionarioDao.findById(id);
        funcionarioBuscado.get().setEstado_funcionario(false);
        funcionarioDao.save(funcionarioBuscado.get());
    }

    @Transactional
    public Funcionario findById(long id) {
        return funcionarioDao.findById(id).orElse(null);
    }

}
