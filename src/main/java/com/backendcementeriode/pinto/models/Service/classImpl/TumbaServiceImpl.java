package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.ITumbaDao;
import com.backendcementeriode.pinto.models.Entity.Derecho;
import com.backendcementeriode.pinto.models.Entity.Tumba;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.ITumbaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TumbaServiceImpl implements ITumbaService {

    @Autowired
    public ITumbaDao tumbaDao;

    @Override
    public List<Tumba> findAll() {
        return tumbaDao.findAll();
    }

    @Override
    public Tumba save(Tumba tumba) {
        return tumbaDao.save(tumba);
    }

    @Override
    public Optional<Tumba> findOne(long id) {
        return tumbaDao.findById(id);
    }

    @Override
    public Tumba findById(long id) {
        return tumbaDao.findById(id).orElse(null);
    }
}
