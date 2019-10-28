package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.Cliente;
import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.Derecho;
import com.backendcementeriode.pinto.models.Service.classImpl.ContratoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

public class ContratoController {
    @Autowired
    private ContratoServiceImpl contratoService;

    ////-------------- Listar Clientes ---------------------////
    @RequestMapping(value = "/listContratos", method = RequestMethod.GET)
    public List<Contrato> findAll(){
        List<Contrato> all = contratoService.findAll();
        return all;
    }

    ////-------------- Guardar Clientes ---------------------////

    @PostMapping(value = "/saveContrato")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Contrato contrato){
        Contrato contrato1= null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            contrato1= contratoService.save(contrato);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El contrato ha sido creado con éxito!");
        response.put("Contrato",contrato1);

        return new ResponseEntity<Map<String,Object>>(response, OK);
    }

    @PutMapping(value ="/updateContrato/{id}")
    public ResponseEntity<?> update(@RequestBody Contrato contrato, @PathVariable Long id) {
        Contrato contrato1=contratoService.findOne(id).get();
        Contrato contrato2=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(contrato1==null) {
            response.put("mensaje","No se pudo editar, el contrato con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            contrato1.setId_contrato(contrato.getId_contrato());
            contrato1.setCementerio(contrato.getCementerio());
            contrato1.setTerreno(contrato.getTerreno());
            contrato1.setPatio(contrato.getPatio());
            contrato1.setTumba(contrato.getTumba());
            contrato1.setTipoTumba(contrato.getTipoTumba());
            contrato1.setDerecho(contrato.getDerecho());
            contrato1.setCliente(contrato.getCliente());
            contrato1.setPagosDerecho(contrato.getPagosDerecho());

            contrato2=contratoService.save(contrato1);
            //llamar al service de tumbadifunto para poder generar el "entierro del muertito"
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el contrato en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","La contrato ha sido actualizado con éxito!");
        response.put("Contrato",contrato2);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }
}
