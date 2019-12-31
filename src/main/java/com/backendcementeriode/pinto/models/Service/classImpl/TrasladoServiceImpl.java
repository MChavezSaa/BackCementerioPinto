package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.ITrasladoDao;
import com.backendcementeriode.pinto.models.Entity.Traslado;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.ITrasladoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrasladoServiceImpl implements ITrasladoService {
    @Autowired
    private ITrasladoDao trasladoDao;

    @Transactional
    public List<Traslado> findAll() {
        return trasladoDao.findAll();
    }

    @Transactional
    public Traslado save(Traslado traslado) {
        return trasladoDao.save(traslado);
    }

    @Transactional
    public Traslado findOne(long id) {
        return trasladoDao.findById(id).get();
    }
}
