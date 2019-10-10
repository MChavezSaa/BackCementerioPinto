package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.IPagosDerechoDao;
import com.backendcementeriode.pinto.models.Entity.PagosDerecho;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IPagosDerechoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagosDerechoServiceImpl implements IPagosDerechoService {
    @Autowired
    private IPagosDerechoDao pagosDerechoDao;

    @Override
    public List<PagosDerecho> findAll() {
        return pagosDerechoDao.findAll();
    }

    @Override
    public PagosDerecho save(PagosDerecho pagosDerecho) {
        return pagosDerechoDao.save(pagosDerecho);
    }

    @Override
    public Optional<PagosDerecho> findById(long id) {
        return pagosDerechoDao.findById(id);
    }
}
