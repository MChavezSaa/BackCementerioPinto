package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Funcionario;
import com.backendcementeriode.pinto.models.Entity.Patio;
import com.backendcementeriode.pinto.models.Service.classImpl.PatioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class patioController {

    @Autowired
    public PatioServiceImpl patioService;

    @RequestMapping(value = "/listPatios", method = RequestMethod.GET )
    public List<Patio> findAll() {
        List<Patio> all = patioService.findAll();
        return all;
    }



}
