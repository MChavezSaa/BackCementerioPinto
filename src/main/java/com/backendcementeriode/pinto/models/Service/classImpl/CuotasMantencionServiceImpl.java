package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.ICuotasMantencionDao;
import com.backendcementeriode.pinto.models.Entity.CuotasMantencion;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.ICuotasMantencion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CuotasMantencionServiceImpl implements ICuotasMantencion {

    @Autowired
    public ICuotasMantencionDao cuotasMantencionDao;

    @Transactional
    public List<CuotasMantencion> findAll() {
        return cuotasMantencionDao.findAll();
    }

    @Transactional
    public CuotasMantencion save(CuotasMantencion cuotasMantencion) {
        return cuotasMantencionDao.save(cuotasMantencion);
    }

    @Transactional
    public Optional<CuotasMantencion> findOne(long id) {
        return cuotasMantencionDao.findById(id);
    }

    @Transactional
    public void delete(CuotasMantencion cuotasMantencion) {
        cuotasMantencion.setEstadoCM(true);
        cuotasMantencionDao.save(cuotasMantencion);
    }

    @Transactional
    public Optional<CuotasMantencion> findById(long id) {
        return cuotasMantencionDao.findById(id);
    }
}
