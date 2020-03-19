package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Derecho;
import com.backendcementeriode.pinto.models.Service.classImpl.DechoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "*")
@RestController
public class DerechoController {
    @Autowired
    private DechoServiceImpl derechoService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listDerechos", method = RequestMethod.GET )
    public List<Derecho> findAll() {
        List<Derecho> all = derechoService.findAll();
        return all;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findDerecho/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Derecho derecho=null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            derecho=derechoService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(derecho==null) {
            response.put("mensaje","El derecho con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(derecho,HttpStatus.OK);
    }



    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveDerecho")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Derecho derecho) {

        Derecho derecho1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            derecho1 = derechoService.save(derecho);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El derecho ha sido creado con éxito!");
        response.put("Derecho", derecho1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }


    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/updateDerecho/{id}")
    public ResponseEntity<?> update(@RequestBody Derecho derecho, @PathVariable Long id) {
        Derecho derecho1=derechoService.findById(id);
        Derecho derecho2=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(derecho1==null) {
            response.put("mensaje","No se pudo editar, el derecho con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            derecho1.setId_Derecho(derecho.getId_Derecho());
            derecho1.setContrato(derecho.getContrato());
            derecho1.setEstadoDerecho(derecho.isEstadoDerecho());
            derecho1.setFecha_Inscripcion_Derecho(derecho.getFecha_Inscripcion_Derecho());
            derecho1.setFecha_Pago_Derecho(derecho.getFecha_Pago_Derecho());
            derecho1.setFecha_Vencimiento_Derecho(derecho.getFecha_Vencimiento_Derecho());
            derecho1.setNumero_Cuotas_Derecho(derecho.getNumero_Cuotas_Derecho());
            derecho1.setValor_Cuota_Derecho(derecho.getValor_Cuota_Derecho());
            derecho1.setMedioPago_Derecho(derecho.getMedioPago_Derecho());


            derecho2=derechoService.save(derecho1);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el derecho en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","La derecho ha sido actualizado con éxito!");
        response.put("Derecho",derecho2);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }
}
