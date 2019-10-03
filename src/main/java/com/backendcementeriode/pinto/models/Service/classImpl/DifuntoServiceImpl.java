package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.IDifuntoDao;
import com.backendcementeriode.pinto.models.Entity.Derecho;
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
        difuntoADesactivar.setEstadoDifunto(false);
        difuntoDao.save(difuntoADesactivar);
    }

    @Transactional
    public void deletebyID(long id) {
        Optional<Difunto> difuntoADesactivar = difuntoDao.findById(id);
        Difunto difuntoADesactivar2 = null;

        if (difuntoADesactivar != null) {
            difuntoADesactivar2.setId_Difunto(difuntoADesactivar.get().getId_Difunto());
            difuntoADesactivar2.setApellidoP_Difunto(difuntoADesactivar.get().getApellidoP_Difunto());
            difuntoADesactivar2.setApellidoM_Difunto(difuntoADesactivar.get().getApellidoM_Difunto());
            difuntoADesactivar2.setCertificado_Defuncion(difuntoADesactivar.get().getCertificado_Defuncion());
            difuntoADesactivar2.setFecha_Defuncion(difuntoADesactivar.get().getFecha_Defuncion());
            difuntoADesactivar2.setFecha_Inscripcion_Difunto(difuntoADesactivar.get().getFecha_Inscripcion_Difunto());
            difuntoADesactivar2.setFecha_Nacimiento_Difunto(difuntoADesactivar.get().getFecha_Nacimiento_Difunto());
            difuntoADesactivar2.setFotocopia_Carnet(difuntoADesactivar.get().getFotocopia_Carnet());
            difuntoADesactivar2.setGenero_Difunto(difuntoADesactivar.get().getGenero_Difunto());
            difuntoADesactivar2.setNombreC_Madre(difuntoADesactivar.get().getNombreC_Madre());
            difuntoADesactivar2.setNombreC_Padre(difuntoADesactivar.get().getNombreC_Padre());
            difuntoADesactivar2.setNombres_Difunto(difuntoADesactivar.get().getNombres_Difunto());
            difuntoADesactivar2.setRut_Difunto(difuntoADesactivar.get().getRut_Difunto());
            difuntoADesactivar2.setSacramento1(difuntoADesactivar.get().isSacramento1());
            difuntoADesactivar2.setSacramento2(difuntoADesactivar.get().isSacramento2());
            difuntoADesactivar2.setSacramento3(difuntoADesactivar.get().isSacramento3());
            difuntoADesactivar2.setSacramento4(difuntoADesactivar.get().isSacramento4());

            difuntoDao.save(difuntoADesactivar2);
        }


    }

    @Transactional
    public Difunto findById(long id) {
        return difuntoDao.findById(id).orElse(null);
    }
}
