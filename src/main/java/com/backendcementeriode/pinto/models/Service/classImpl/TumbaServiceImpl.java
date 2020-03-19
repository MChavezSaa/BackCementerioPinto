package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.ITumbaDao;
import com.backendcementeriode.pinto.models.Entity.Tumba;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.ITumbaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TumbaServiceImpl implements ITumbaService {

    @Autowired(required = true)
    public ITumbaDao tumbaDao;

    @Transactional
    public List<Tumba> findAll() {
        return tumbaDao.findAll();
    }

    @Transactional
    public Tumba save(Tumba tumba) {
        return tumbaDao.save(tumba);
    }

    @Transactional
    public Optional<Tumba> findOne(long id) {
        return tumbaDao.findById(id);
    }

    @Transactional
    public Tumba findById(long id) {
        return tumbaDao.findById(id).get();
    }

    @Transactional
    public List<Tumba> findFreetumbs() {
        return tumbaDao.findFreetumbs();
    }

    @Transactional
    public List<Tumba> findOcupadotumbs() {
        return tumbaDao.findOcupadotumbs();
    }

    @Transactional
    public List<Tumba> findReservadotumbs(){
        return tumbaDao.findReservadotumbs();
    }
}
