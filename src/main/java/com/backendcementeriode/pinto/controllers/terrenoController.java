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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class terrenoController {

    @Autowired
    private TerrenoServiceImpl terrenoService;

    @RequestMapping(value = "/listTerrenos", method = RequestMethod.GET)
    public List<Terreno> findAll() {
        List<Terreno> all = terrenoService.findAll();
        return all;
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

        Map<String, Object> response = new HashMap<String, Object>();
        try {
            terrenoService.deletebyID(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al deshabilitar el terreno de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El Terreno fue deshabilitado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

}
