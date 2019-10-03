package com.backendcementeriode.pinto.models.Service.classImpl;


import com.backendcementeriode.pinto.models.Dao.IDerechoDao;
import com.backendcementeriode.pinto.models.Entity.Derecho;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IDerechoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DechoServiceImpl implements IDerechoService {

    @Autowired
    public IDerechoDao derechoDao;


    @Override
    public List<Derecho> findAll() {
        return derechoDao.findAll();
    }

    @Override
    public Derecho save(Derecho derecho) {
        return derechoDao.save(derecho);
    }

    @Override
    public Optional<Derecho> findOne(long id) {
        return derechoDao.findById(id);
    }

    @Transactional
    public void delete(Derecho derecho) {
        Derecho derechoADesactivar = derecho;
        derechoADesactivar.setEstadoDerecho(false);
        derechoDao.save(derechoADesactivar);
    }

    @Transactional
    public void deletebyID(long id) {
        Optional<Derecho> derechoADesactivar = derechoDao.findById(id);
        Derecho derechoADesactivar2 = null;


    }

    @Transactional
    public Derecho findById(long id) {
        return derechoDao.findById(id).orElse(null);
    }
}
