package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.IPagosMantencionDao;
import com.backendcementeriode.pinto.models.Entity.PagosMantencion;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IPagosMantencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PagosMantencionServiceImpl implements IPagosMantencionService {
    @Autowired
    private IPagosMantencionDao pagosMantencionDao;

    @Override
    public List<PagosMantencion> findAll() {
        return pagosMantencionDao.findAll();
    }

    @Override
    public PagosMantencion save(PagosMantencion pagosMantencion) {
        return pagosMantencionDao.save(pagosMantencion);
    }

    @Override
    public Optional<PagosMantencion> findById(long id) {
        return pagosMantencionDao.findById(id);
    }

    @Override
    @Transactional
    public List<PagosMantencion> cuotasPorIdClienteEnContrato(long id) {

        return pagosMantencionDao.cuotasPorIdClienteEnContrato(id);
    }


}
