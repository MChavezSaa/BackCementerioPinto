package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.ITerrenoDao;
import com.backendcementeriode.pinto.models.Entity.Terreno;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.ITerrenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public  class TerrenoServiceImpl implements ITerrenoService {

    @Autowired
    public ITerrenoDao terrenoDao;


    @Override
    public List<Terreno> findAll() {

        return terrenoDao.findAll();
    }

    @Override
    public void save(Terreno terreno) {
        terrenoDao.save(terreno);
    }

    @Override
    public Optional<Terreno> findOne(long id) {

        return terrenoDao.findById(id);
    }

    @Override
    public void delete(Terreno terreno) {
        terrenoDao.delete(terreno);
    }
}
