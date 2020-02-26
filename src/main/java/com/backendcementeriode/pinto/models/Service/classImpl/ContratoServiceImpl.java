package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.IContratoDao;
import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.CuotasMantencion;
import com.backendcementeriode.pinto.models.Entity.PagosMantencion;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoServiceImpl implements IContratoService {

    @Autowired
    public  IContratoDao contratoDao;

    @Override
    @Transactional
    public List<Contrato> findAll() {
        return (List<Contrato>) contratoDao.findAll();
    }

    @Override
    @Transactional
    public Contrato save(Contrato contrato) {
        return contratoDao.save(contrato);
    }

    @Override
    @Transactional
    public Optional<Contrato> findOne(long id) {
        return contratoDao.findById(id);
    }

    @Transactional
    public Contrato findById(long id) {
        return contratoDao.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Contrato contrato) {
        contrato.setEstado_Contrato(false);
        contratoDao.save(contrato);
    }

    @Transactional
    public void deletebyID(long id) {
        Contrato contratoBuscado = contratoDao.findById(id).get();
        contratoBuscado.setEstado_Contrato(false);
        contratoDao.save(contratoBuscado);
    }

    @Transactional
    public List<Object> distincCliente() {
        return contratoDao.distincCliente();
    }

    @Transactional
    public List<Object> getContratoFechas(LocalDate f1 , LocalDate f2) {
        return contratoDao.getContratoFechas(f1, f2);
    }


}
