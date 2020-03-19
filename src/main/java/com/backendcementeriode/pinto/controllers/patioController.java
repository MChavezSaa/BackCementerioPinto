package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Patio;
import com.backendcementeriode.pinto.models.Entity.Tumba;
import com.backendcementeriode.pinto.models.Service.classImpl.PatioServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.TumbaServiceImpl;
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
public class patioController {

    @Autowired
    public PatioServiceImpl patioService;
    @Autowired
    public TumbaServiceImpl tumbaService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listPatios", method = RequestMethod.GET )
    public List<Patio> findAll() {
        List<Patio> all = patioService.findAll();
        return all;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/savePatio")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Patio patio) {
        patio.setEstado_Patio(true);
        Patio patio1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            patio1 = patioService.save(patio);
            Tumba tumba;
            int cont=1;
            for (int i = 1; i <=patio.getCapacidad_Patio() ; i++) {
                tumba = new Tumba();
                tumba.setNumero_Tumba(i);
                tumba.setValor_Tumba(0);
                tumba.setOrientacion_Tumba("Por Definir");
                tumba.setLargo(0);
                tumba.setAncho(0);
                tumba.setPatio(patio);
                tumba.setTipo_Tumba(null);
                tumba.setCliente(null);
                tumba.setEstado_Tumba("Disponible");
                if(patio.getNombreTT().equalsIgnoreCase("Sector Nichos")) {
                    tumba.setNivel(cont);
                    cont++;
                }else{
                    tumba.setNivel(0);
                }

                if(cont>=4){
                    cont =1;
                }
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

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/DeletePatio/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Patio patioB = patioService.findById(id);
        Patio patio2 = null;

        Map<String, Object> response = new HashMap<String, Object>();
        try {
            patioB.setEstado_Patio(false);
            patio2 = patioService.save(patioB);
            //patioService.deletebyID(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al deshabilitar el patio de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El patio fue deshabilitado con éxito!");
        response.put("Patio: ", patio2);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value="/CambiaEstadoPatio/{id}")
    public ResponseEntity<?>darAlta(@RequestBody Patio patio, @PathVariable Long id){
        Patio patioActual = patioService.findById(id);
        patioActual.setEstado_Patio(true);
        Patio patioUpdate = null;

        Map<String, Object> response = new HashMap<String, Object>();

        if(patioActual==null) {
            response.put("mensaje","No se pudo cambiar el estado del Patio con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            patioUpdate= patioService.save(patioActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al cambiar el estado del patio de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El patio ha cambiado de estado con éxito!");
        response.put("Patio: ", patioUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }


    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findPatio/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Patio patio=null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            patio=patioService.findOne(id).get();
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(patio==null) {
            response.put("mensaje","El patio con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(patio,HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/updatePatio/{id}")
    public ResponseEntity<?> update(@RequestBody Patio patio, @PathVariable Long id) {
        Patio patio1=patioService.findOne(id).get();
        Patio patio2=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(patio1==null) {
            response.put("mensaje","No se pudo editar, el patio con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            patio1.setCapacidad_Patio(patio.getCapacidad_Patio());
            patio1.setNombre_Patio(patio.getNombre_Patio());
            patio1.setTerreno(patio.getTerreno());
            patio2=patioService.save(patio1);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar la patio en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","La patio ha sido actualizado con éxito!");
        response.put("patio",patio2);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }



}
