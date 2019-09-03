package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.Occiso;
import com.backendcementeriode.pinto.models.Service.OccisoServiceImpl;
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
public class occisoController {
        @Autowired
        private OccisoServiceImpl occisoService;

        @RequestMapping(value = "/listOcciso", method = RequestMethod.GET)
        public List<Occiso> findAll() {
            List<Occiso> all = occisoService.findAll();
            return all;
        }

        @PostMapping(value = "/saveOcciso")
        @ResponseStatus(value = CREATED)
        public ResponseEntity<?> create(@RequestBody Occiso occiso) {

            Occiso occiso1 = null;
            Map<String, Object> response = new HashMap<String, Object>();

            try {
                occiso1 = occisoService.save(occiso);
            } catch (DataAccessException e) {
                response.put("mensaje", "Error al realizar el insert en la base de datos");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
            }

            response.put("mensaje", "El occiso ha sido creado con éxito!");
            response.put("Funcionario", occiso1);

            return new ResponseEntity<Map<String, Object>>(response, OK);
        }


        @RequestMapping(value = "/DeleteOcciso/{id}", method = RequestMethod.DELETE)
        public ResponseEntity<?> delete(@PathVariable Long id) {

            Map<String, Object> response = new HashMap<String, Object>();
            try {
                occisoService.deletebyID(id);
            } catch (DataAccessException e) {
                response.put("mensaje", "Error al eliminar el occiso de la base de datos");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            response.put("mensaje", "El funcionario fue eliminado con éxito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        }

        @PutMapping(value = "/updateOcciso/{id}")
        public ResponseEntity<?> update(@RequestBody Occiso occiso, @PathVariable Long id) {
            Occiso occisoActual = occisoService.findById(id);
            Occiso occisoUpdated = null;

            Map<String, Object> response = new HashMap<String, Object>();

            if (occisoActual == null) {
                response.put("mensaje", "No se pudo editar, el occiso con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
            try {
                occisoActual.setId_Occiso(occiso.getId_Occiso());
                occisoActual.setNombres(occiso.getNombres());
                occisoActual.setApellidos(occiso.getApellidos());
                occisoActual.setFecha_defuncion(occiso.getFecha_defuncion());

                occisoUpdated = occisoService.save(occisoActual);
            } catch (DataAccessException e) {
                response.put("mensaje", "Error al actualizar el occiso en la base de datos");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            response.put("mensaje", "El occiso ha sido actualizado con éxito!");
            response.put("Occioso", occisoUpdated);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        }

    }
