package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.Terreno;
import com.backendcementeriode.pinto.models.Service.classImpl.TerrenoServiceImpl;
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
public class terrenoController {

    @Autowired
    private TerrenoServiceImpl terrenoService;

    @RequestMapping(value = "/listTerrenos", method = RequestMethod.GET)
    public List<Terreno> findAll() {
        List<Terreno> all = terrenoService.findAll();
        return all;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findTerreno/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Terreno terreno=null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            terreno=terrenoService.findOne(id).get();
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(terreno==null) {
            response.put("mensaje","El terreno con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(terreno,HttpStatus.OK);
    }


    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveTerrenos")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Terreno terreno) {
        terreno.setEstado_Terreno(true);
        Terreno terreno1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            terreno1 = terrenoService.save(terreno);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El terreno ha sido creado con éxito!");
        response.put("Funcionario", terreno1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/DeleteTerreno/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Terreno terrenoB = terrenoService.findById(id);
        Terreno terreno2 = null;

        Map<String, Object> response = new HashMap<String, Object>();
        try {
            terrenoB.setEstado_Terreno(false);
            terreno2= terrenoService.save(terrenoB);
            //terrenoService.deletebyID(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al deshabilitar el terreno de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El Terreno fue deshabilitado con éxito!");
        response.put("Terreno: ", terreno2);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/CambiaEstadoTerreno/{id}")
    public ResponseEntity<?> darAlta(@RequestBody Terreno terreno, @PathVariable Long id) {
        Terreno terrenoActual= terrenoService.findById(id);
        terrenoActual.setEstado_Terreno(true);
        Terreno terrenoUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(terrenoActual==null) {
            response.put("mensaje","No se pudo cambiar el estado del Terreno con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            terrenoUpdated= terrenoService.save(terrenoActual);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al cambiar el estado del terreno en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El terreno ha cambiado de estado con éxito!");
        response.put("Terreno: ",terrenoUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }


    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/updateTerreno/{id}")
    public ResponseEntity<?> update(@RequestBody Terreno terreno, @PathVariable Long id) {
        Terreno terreno1=terrenoService.findOne(id).get();
        Terreno terreno2=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(terreno1==null) {
            response.put("mensaje","No se pudo editar, el terreno con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            terreno1.setCapacidad_Terreno(terreno.getCapacidad_Terreno());
            terreno1.setCementerio(terreno.getCementerio());
            terreno1.setNombre_Terreno(terreno.getNombre_Terreno());
            terreno2=terrenoService.save(terreno1);

        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el terreno en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El terreno ha sido actualizado con éxito!");
        response.put("terreno",terreno2);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }


}
