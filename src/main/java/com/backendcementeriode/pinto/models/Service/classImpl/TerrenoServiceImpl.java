package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.ITerrenoDao;
import com.backendcementeriode.pinto.models.Entity.Terreno;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.ITerrenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public  class TerrenoServiceImpl implements ITerrenoService {

    @Autowired
    public ITerrenoDao terrenoDao;


    @Transactional
    public List<Terreno> findAll() {

        return terrenoDao.findAll();
    }

    @Transactional
    public Terreno save(Terreno terreno) {
        return terrenoDao.save(terreno);
    }

    @Transactional
    public Optional<Terreno> findOne(long id) {

        return terrenoDao.findById(id);
    }

    @Transactional
    public void delete(Terreno terreno) {
        terreno.setEstado_Terreno(false);
        terrenoDao.save(terreno);
    }

    @Transactional
    public void deletebyID(long id) {
        Terreno terrenoB = terrenoDao.findById(id).get();
        terrenoB.setEstado_Terreno(false);
        terrenoDao.save(terrenoB);
    }

    @Override
    public Terreno findById(long id) {
        return terrenoDao.findById(id).orElse(null);
    }
}
