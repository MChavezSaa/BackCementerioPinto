package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Patio;
import com.backendcementeriode.pinto.models.Service.classImpl.PatioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

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

    @PostMapping(value = "/savePatio")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Patio patio) {

        Patio patio1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            patio1 = patioService.save(patio);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El patio ha sido creado con éxito!");
        response.put("Funcionario", patio1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }

    @RequestMapping(value = "/DeletePatio/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<String, Object>();
        try {
            patioService.deletebyID(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al deshabilitar el patio de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El patio fue deshabilitado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }



}
