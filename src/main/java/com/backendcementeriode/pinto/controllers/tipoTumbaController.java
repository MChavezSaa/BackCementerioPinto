package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.TipoTumba;
import com.backendcementeriode.pinto.models.Service.classImpl.TipoTumbaServiceImpl;
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
public class tipoTumbaController {

    @Autowired
    public TipoTumbaServiceImpl tipoTumbaService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listTipotumbas", method = RequestMethod.GET )
    public List<TipoTumba> findAll() {
        List<TipoTumba> all = tipoTumbaService.findAll();
        return all;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findTipoTumba/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        TipoTumba tipoTumba=null;
        Map<String,Object> response =new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            tipoTumba=tipoTumbaService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if(tipoTumba==null) {
            response.put("mensaje","El tipo de tumba con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(tipoTumba,HttpStatus.OK);
    }


    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveTipoTumba")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody TipoTumba tipotumba) {
        tipotumba.setEstado_tipo_tumba(true);
        TipoTumba tipoTumba = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            tipoTumba = this.tipoTumbaService.save(tipotumba);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El tipo de tumba ha sido creado con éxito!");
        response.put("Funcionario", tipoTumba);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/updateTipoTumba/{id}")
    @ResponseStatus(value = CREATED)
    public  ResponseEntity<?> update(@RequestBody TipoTumba tipoTumba, @PathVariable Long id){
        TipoTumba tipoTumbaActual=tipoTumbaService.findById(id);
        TipoTumba tipoTumbaUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(tipoTumbaActual==null) {
            response.put("mensaje","No se pudo editar, el tipo de tumba con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
           // tipoTumbaActual.setId_TipoTumba(tipoTumba.getId_TipoTumba());
            tipoTumbaActual.setCapacidad_tipo_tumba(tipoTumba.getCapacidad_tipo_tumba());
            tipoTumbaActual.setNombretipo_tumba(tipoTumba.getNombretipo_tumba());
            //tipoTumbaActual.setEstado_tipo_tumba(tipoTumba.isEstado_tipo_tumba());
            tipoTumbaUpdated=tipoTumbaService.save(tipoTumbaActual);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el funcionario en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El Tipo de tumba ha sido actualizado con éxito!");
        response.put("Tipo Tumba",tipoTumbaUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/DeleteTipoTumba/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        TipoTumba tipoT1 = tipoTumbaService.findById(id);
        TipoTumba tipoT2 = null;

        Map<String, Object> response = new HashMap<String, Object>();
        try {
            tipoT1.setEstado_tipo_tumba(false);
            tipoT2= tipoTumbaService.save(tipoT1);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al deshabilitar el tipo de tumba de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El tipo de tumba fue deshabilitado con éxito!");
        response.put("Tipo de Tumba: ", tipoT2);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/CambiaEstadoTipoTumba/{id}")
    public ResponseEntity<?> darAlta(@RequestBody TipoTumba tipoTumba, @PathVariable Long id) {
        TipoTumba tipoA= tipoTumbaService.findById(id);
        tipoA.setEstado_tipo_tumba(true);
        TipoTumba tipoUpdate=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(tipoA==null) {
            response.put("mensaje","No se pudo cambiar el estado del Tipo de tumba con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            tipoUpdate= tipoTumbaService.save(tipoA);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al cambiar el estado del tipo de tumba en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El tipo de tumba ha cambiado de estado con éxito!");
        response.put("Tipo de Tumba: ",tipoUpdate);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }


}
