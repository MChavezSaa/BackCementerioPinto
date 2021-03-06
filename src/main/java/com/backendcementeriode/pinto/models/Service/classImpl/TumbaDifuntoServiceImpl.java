package com.backendcementeriode.pinto.models.Service.classImpl;


import com.backendcementeriode.pinto.models.Dao.ITumbaDifuntoDao;
import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.Tumba_Difunto;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.ITumbaDifuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class TumbaDifuntoServiceImpl implements ITumbaDifuntoService {

    @Autowired
   public ITumbaDifuntoDao tumbaDifuntoDao;


   @Transactional
    public List<Tumba_Difunto> findAll() {
        return tumbaDifuntoDao.findAll();
    }

    @Transactional
    public Tumba_Difunto save(Tumba_Difunto tumbadifunto) {
        return tumbaDifuntoDao.save(tumbadifunto);
    }

    @Transactional
    public Optional<Tumba_Difunto> findOne(long id) {
        return tumbaDifuntoDao.findById(id);
    }

    @Transactional
    public Optional<Tumba_Difunto> findById(long id) {
        return tumbaDifuntoDao.findById(id);
    }

    @Transactional
    public Tumba_Difunto contratoPorDifunto(long id) {
        return tumbaDifuntoDao.contratoPorDifunto(id);
    }

    @Transactional
    public List<Tumba_Difunto> ListaValidacionTraslado(String idTumba) {
        return tumbaDifuntoDao.ListaValidacionTraslado(idTumba);
    }

    @Transactional
    public List<Tumba_Difunto> ListaValidacion2(long idDifunto) {
        return tumbaDifuntoDao.ListaValidacion2(idDifunto);
    }

    @Transactional
    public Tumba_Difunto prueba(long idDifunto) {
        return tumbaDifuntoDao.prueba(idDifunto);
    }
}
