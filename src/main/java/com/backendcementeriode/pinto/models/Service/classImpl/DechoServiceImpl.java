package com.backendcementeriode.pinto.models.Service.classImpl;


import com.backendcementeriode.pinto.models.Dao.IDerechoDao;
import com.backendcementeriode.pinto.models.Entity.Derecho;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IDerechoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DechoServiceImpl implements IDerechoService {

    @Autowired
    public IDerechoDao derechoDao;


    @Override
    public List<Derecho> findAll() {

        return derechoDao.findAll();
    }

    @Override
    public Derecho save(Derecho derecho) {

        return derechoDao.save(derecho);
    }

    @Override
    public Optional<Derecho> findOne(long id) {

        return derechoDao.findById(id);
    }

    @Transactional
    public void delete(Derecho derecho) {
        Derecho derechoADesactivar = derecho;
        derechoADesactivar.setEstadoDerecho(false);
        derechoDao.save(derechoADesactivar);
    }

    @Transactional
    public void deletebyID(long id) {
        Optional<Derecho> derechoADesactivar = derechoDao.findById(id);
        Derecho derechoADesactivar2 = null;

        if (derechoADesactivar != null){
            derechoADesactivar2.setId_Derecho(derechoADesactivar.get().getId_Derecho());
            derechoADesactivar2.setFecha_Inscripcion_Derecho(derechoADesactivar.get().getFecha_Inscripcion_Derecho());
            derechoADesactivar2.setFecha_Pago_Derecho(derechoADesactivar.get().getFecha_Pago_Derecho());
            derechoADesactivar2.setFecha_Vencimiento_Derecho(derechoADesactivar.get().getFecha_Vencimiento_Derecho());
            derechoADesactivar2.setNumero_Cuotas_Derecho(derechoADesactivar.get().getNumero_Cuotas_Derecho());
            derechoADesactivar2.setValor_Cuota_Derecho(derechoADesactivar.get().getValor_Cuota_Derecho());
            derechoADesactivar2.setCliente(derechoADesactivar.get().getCliente());
        }

    }

    @Transactional
    public Derecho findById(long id) {
        return derechoDao.findById(id).orElse(null);
    }
}
