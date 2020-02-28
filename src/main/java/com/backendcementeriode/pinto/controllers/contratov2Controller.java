package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Cliente;
import com.backendcementeriode.pinto.models.Entity.contratov2;
import com.backendcementeriode.pinto.models.Service.classImpl.contratov2ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class contratov2Controller {
    @Autowired
    private contratov2ServiceImpl contratov2Service;


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listContratoV2", method = RequestMethod.GET)
    public List<contratov2> findAll(){
        List<contratov2> all = contratov2Service.findAll();
        return all;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findcontratoV2/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        contratov2 contratov2=null;
        Map<String,Object> response =new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            contratov2=contratov2Service.findOne(id).get();
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if(contratov2==null) {
            response.put("mensaje","El contratov2 con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(contratov2,HttpStatus.OK);
    }


}
