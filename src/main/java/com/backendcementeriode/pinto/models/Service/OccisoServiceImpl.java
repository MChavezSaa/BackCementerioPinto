package com.backendcementeriode.pinto.models.Service;


import com.backendcementeriode.pinto.models.Dao.IOccisoDao;
import com.backendcementeriode.pinto.models.Entity.Occiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OccisoServiceImpl implements  IOccisoService{

    @Autowired
    private IOccisoDao occisoDao;

    @Transactional
    public List<Occiso> findAll() {

        return occisoDao.findAll();
    }

    @Transactional
    public Occiso save(Occiso occiso) {
        return occisoDao.save(occiso);
    }

    @Transactional
    public Optional<Occiso> findOne(long id) {

        return occisoDao.findById(id);
    }

    @Transactional
    public void delete(Occiso occiso) {
        occisoDao.delete(occiso);
    }

    @Transactional
    public void deletebyID(long id) {
        occisoDao.deleteById(id);
    }

    @Transactional
    public Occiso findById(long id) {
        return occisoDao.findById(id).orElse(null);
    }
}
