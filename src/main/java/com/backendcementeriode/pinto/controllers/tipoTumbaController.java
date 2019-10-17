package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Funcionario;
import com.backendcementeriode.pinto.models.Entity.TipoTumba;
import com.backendcementeriode.pinto.models.Service.classImpl.TipoTumbaServiceImpl;
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
public class tipoTumbaController {

    @Autowired
    public TipoTumbaServiceImpl tipoTumbaService;

    @RequestMapping(value = "/listTipotumbas", method = RequestMethod.GET )
    public List<TipoTumba> findAll() {
        List<TipoTumba> all = tipoTumbaService.findAll();
        return all;
    }

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

    @PutMapping(value = "/updateTipoTumba/{id}")
    @ResponseStatus(value = CREATED)
    public  ResponseEntity<?> update(@RequestBody TipoTumba tipoTumba, @PathVariable Long id){
        TipoTumba tipoTumbaActual=tipoTumbaService.findById(id).get();
        TipoTumba tipoTumbaUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(tipoTumbaActual==null) {
            response.put("mensaje","No se pudo editar, el tipo de tumba con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            tipoTumbaActual.setId_TipoTumba(tipoTumba.getId_TipoTumba());
            tipoTumbaActual.setCapacidad_tipo_tumba(tipoTumba.getCapacidad_tipo_tumba());
            tipoTumbaActual.setNombre_tipo_tumba(tipoTumba.getNombre_tipo_tumba());
            tipoTumbaActual.setEstado_tipo_tumba(tipoTumba.isEstado_tipo_tumba());
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

}
