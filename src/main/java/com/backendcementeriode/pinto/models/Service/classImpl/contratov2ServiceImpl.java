package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Entity.contratov2;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IContratov2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class contratov2ServiceImpl implements IContratov2Service {


    @Autowired
    private IContratov2Service contratov2Service;

    @Override
    public List<contratov2> findAll() {
        return contratov2Service.findAll();
    }

    @Override
    public contratov2 save(contratov2 contrato) {
        return contratov2Service.save(contrato);
    }

    @Override
    public Optional<contratov2> findOne(long id) {
        return contratov2Service.findOne(id);

    }
}
