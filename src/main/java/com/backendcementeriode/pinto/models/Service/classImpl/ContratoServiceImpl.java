package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.IContratoDao;
import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoServiceImpl implements IContratoService {

    @Autowired
    public  IContratoDao contratoDao;

    @Override
    @Transactional
    public List<Contrato> findAll() {
        return (List<Contrato>) contratoDao.findAll();
    }

    @Override
    @Transactional
    public Contrato save(Contrato contrato) {
        return contratoDao.save(contrato);
    }

    @Override
    @Transactional
    public Optional<Contrato> findOne(long id) {
        return contratoDao.findById(id);
    }
}
