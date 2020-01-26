package com.backendcementeriode.pinto.models.Service.classImpl;

import com.backendcementeriode.pinto.models.Dao.IContrato2Dao;
import com.backendcementeriode.pinto.models.Entity.contratov2;
import com.backendcementeriode.pinto.models.Service.SeviceInterface.IContratov2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class contratov2ServiceImpl implements IContratov2Service {


    @Autowired
    private IContrato2Dao contratov2Dao;

    @Override
    @Transactional
    public List<contratov2> findAll() {
        return (List<contratov2>) contratov2Dao.findAll();
    }

    @Override
    @Transactional
    public void save(contratov2 contratov2) {
         contratov2Dao.save(contratov2);
    }

    @Override
    @Transactional
    public Optional<contratov2> findOne(long id) {
        return contratov2Dao.findById(id);

    }
}
