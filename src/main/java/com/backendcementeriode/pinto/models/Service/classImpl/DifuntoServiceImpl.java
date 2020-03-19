package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.IDifuntoDao;
import com.backendcementeriode.pinto.models.Entity.Difunto;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IDifuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DifuntoServiceImpl implements IDifuntoService {

    @Autowired
    public IDifuntoDao difuntoDao;

    @Transactional
    public List<Difunto> findAll() {
        return difuntoDao.findAll();
    }

    @Transactional
    public Difunto save(Difunto difunto) {
        return difuntoDao.save(difunto);
    }

    @Override
    public Optional<Difunto> findOne(long id) {
        return difuntoDao.findById(id);
    }

    @Transactional
    public void delete(Difunto difunto) {
        Difunto difuntoADesactivar = difunto;
        difuntoADesactivar.setEstadoDifunto("Reduccion");
        difuntoDao.save(difuntoADesactivar);
    }

    @Transactional
    public void deletebyID(long id) {
        Optional<Difunto> difuntoADesactivar = difuntoDao.findById(id);
        difuntoADesactivar.get().setEstadoDifunto("Reduccion");
        difuntoDao.save(difuntoADesactivar.get());
    }

    @Transactional
    public Difunto findById(long id) {
        return difuntoDao.findById(id).orElse(null);
    }

    @Transactional
    public List<Object> difuntosNotIn() {
        return difuntoDao.difuntosNotIn();
    }
}
