package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.ITipoTumbaDao;
import com.backendcementeriode.pinto.models.Entity.TipoTumba;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.ITipoTumbaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class TIpoTumbaServiceImpl implements ITipoTumbaService {
    @Autowired
    public ITipoTumbaDao tipoTumbaDao;

    @Transactional
    public List<TipoTumba> findAll() {
        return tipoTumbaDao.findAll();
    }

    @Transactional
    public TipoTumba save(TipoTumba tipoTumba) {
        return tipoTumbaDao.save(tipoTumba);
    }

    @Transactional
    public void delete(TipoTumba tipoTumba) {
        tipoTumba.setEstado_TipoTumba(false);
        tipoTumbaDao.save(tipoTumba);
    }

    @Transactional
    public void deletebyID(long id) {
        Optional<TipoTumba> tipoTumbaBuscado = tipoTumbaDao.findById(id);
        tipoTumbaBuscado.get().setEstado_TipoTumba(false);
        tipoTumbaDao.save(tipoTumbaBuscado.get());
    }

    @Transactional
    public Optional<TipoTumba> findById(long id) {
        return tipoTumbaDao.findById(id);
    }
}
