package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.IPatioDao;
import com.backendcementeriode.pinto.models.Entity.Patio;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IPatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatioServiceImpl implements IPatioService {

    @Autowired
    public IPatioDao patioDao;

    @Transactional
    public List<Patio> findAll() {
        return patioDao.findAll();
    }

    @Transactional
    public Patio save(Patio patio) {
        return patioDao.save(patio);
    }

    @Transactional
    public Optional<Patio> findOne(long id) {
        return patioDao.findById(id);
    }

    @Transactional
    public Optional<Patio> findById(long id) {
        return patioDao.findById(id);
    }
}
