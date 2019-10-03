package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.ICementerioDao;
import com.backendcementeriode.pinto.models.Entity.Cementerio;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.ICementerioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CementerioServiceImpl implements ICementerioService {

    @Autowired
    public ICementerioDao cementerioDao;

    @Transactional
    public List<Cementerio> findAll() {
        return cementerioDao.findAll();
    }

    @Transactional
    public Cementerio save(Cementerio cementerio) {
        return cementerioDao.save(cementerio);
    }

    @Transactional
    public Optional<Cementerio> findOne(long id) {
        return cementerioDao.findById(id);
    }

    @Transactional
    public Optional<Cementerio> findById(long id) {
        return cementerioDao.findById(id);
    }
}
