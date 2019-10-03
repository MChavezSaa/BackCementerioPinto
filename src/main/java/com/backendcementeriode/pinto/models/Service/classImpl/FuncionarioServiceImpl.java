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

    @Transactional
    public void deletebyID(long id) {
        Optional<Funcionario> funcionarioBuscado = funcionarioDao.findById(id);
        Funcionario funcionarioADesactivar = null;
        if (funcionarioBuscado != null){
            funcionarioADesactivar.setId_funcionario(funcionarioBuscado.get().getId_funcionario());
            funcionarioADesactivar.setNombres_Funcionario(funcionarioBuscado.get().getNombres_Funcionario());
            funcionarioADesactivar.setApellidoP_Funcionario(funcionarioBuscado.get().getApellidoP_Funcionario());
            funcionarioADesactivar.setApellidoM_Funcionario(funcionarioBuscado.get().getApellidoM_Funcionario());
            funcionarioADesactivar.setCargo_Funcionario(funcionarioBuscado.get().getCargo_Funcionario());
            funcionarioADesactivar.setRut_Funcionario(funcionarioBuscado.get().getRut_Funcionario());
            funcionarioADesactivar.setEstado_funcionario(false);
            funcionarioDao.save(funcionarioADesactivar);
        }
    }

    @Transactional
    public Funcionario findById(long id) {
        return funcionarioDao.findById(id).orElse(null);
    }

}
