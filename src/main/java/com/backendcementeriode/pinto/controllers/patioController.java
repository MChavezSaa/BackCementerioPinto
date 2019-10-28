package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Patio;
import com.backendcementeriode.pinto.models.Entity.Tumba;
import com.backendcementeriode.pinto.models.Service.classImpl.PatioServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.TumbaServiceImpl;
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
    @Autowired
    public TumbaServiceImpl tumbaService;

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
            Tumba tumba;
            for (int i = 1; i <=patio.getCapacidad_Patio() ; i++) {
                tumba = new Tumba();
                tumba.setNumero_Tumba(i);
                tumba.setValor_Tumba(0);
                tumba.setOrientacion_Tumba(null);
                tumba.setLargo(0);
                tumba.setAncho(0);
                tumba.setPatio(patio);
                tumba.setTipo_Tumba(null);
                tumba.setCliente(null);
                tumba.setEstado_Tumba(false);
                tumbaService.save(tumba);
            }
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
