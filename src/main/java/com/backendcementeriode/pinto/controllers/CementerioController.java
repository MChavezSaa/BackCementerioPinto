package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.Cementerio;
import com.backendcementeriode.pinto.models.Service.classImpl.CementerioServiceImpl;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CementerioController {
    @Autowired
    private CementerioServiceImpl cementerioService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listCementerios", method = RequestMethod.GET )
    public List<Cementerio> findAll() {
        List<Cementerio> all = cementerioService.findAll();
        return all;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveCementerio")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Cementerio cementerio) {

        Cementerio cementerio1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            cementerio1 = cementerioService.save(cementerio);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cementerio ha sido creado con éxito!");
        response.put("Cementererio", cementerio1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/updateCementerio/{id}")
    public ResponseEntity<?> update(@RequestBody Cementerio cementerio, @PathVariable Long id) {
        Cementerio cementerio1=cementerioService.findById(id).get();
        Cementerio tumba2=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(cementerio1==null) {
            response.put("mensaje","No se pudo editar, el cementerio con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            cementerio1.setId_Cementerio(cementerio.getId_Cementerio());
            cementerio1.setCapacidad_Terrenos(cementerio.getCapacidad_Terrenos());
            cementerio1.setDireccion_Cementerio(cementerio.getDireccion_Cementerio());
            cementerio1.setNombre_Cementerio(cementerio.getNombre_Cementerio());
            cementerio1.setTelefono_Cementerio(cementerio.getTelefono_Cementerio());


            tumba2=cementerioService.save(cementerio1);
            //llamar al service de tumbadifunto para poder generar el "entierro del muertito"
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar la cementerio en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","La cementerio ha sido actualizado con éxito!");
        response.put("cementerio",tumba2);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }
}
