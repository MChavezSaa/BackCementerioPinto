package com.backendcementeriode.pinto.models.Service;


import com.backendcementeriode.pinto.models.Dao.IFuncionarioDao;
import com.backendcementeriode.pinto.models.Entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public  class FuncionarioServiceImpl  implements IFuncionarioService{

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
        funcionarioDao.delete(funcionario);
    }

    @Transactional
    public void deletebyID(long id) {
        funcionarioDao.deleteById(id);
    }

    @Transactional
    public Funcionario findById(long id) {
        return funcionarioDao.findById(id).orElse(null);
    }

}
