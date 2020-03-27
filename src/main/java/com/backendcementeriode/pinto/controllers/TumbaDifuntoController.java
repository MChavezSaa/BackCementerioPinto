package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.Tumba;
import com.backendcementeriode.pinto.models.Entity.Tumba_Difunto;
import com.backendcementeriode.pinto.models.Service.classImpl.TumbaDifuntoServiceImpl;
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
public class TumbaDifuntoController {


    @Autowired
    private TumbaDifuntoServiceImpl tumbaDifuntoService;

    @Autowired
    private TumbaServiceImpl tumbaService;

    @RequestMapping(value = "/listTumbaDifunto", method = RequestMethod.GET)
    public List<Tumba_Difunto> findAll() {
        List<Tumba_Difunto> all = tumbaDifuntoService.findAll();
        return all;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findTumbaDifunto/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Tumba_Difunto tumbaDifunto = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            tumbaDifunto = tumbaDifuntoService.findOne(id).get();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (tumbaDifunto == null) {
            response.put("mensaje", "El registro con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(tumbaDifunto, HttpStatus.OK);
    }


    @RequestMapping(value = "/saveTumbaDifunto", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Tumba_Difunto tumbaDifunto) {
        Tumba_Difunto tumbaDifunto1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            if (tumbaDifunto.getContrato().getTipoTumba().getNombretipo_tumba()
                    .equalsIgnoreCase("doble")) {
                String[] tumbas = tumbaDifunto.getContrato().getTumba().split("-");
                Tumba tumba1 = tumbaService.findById(Long.parseLong(tumbas[0]));
                Tumba tumba2 = tumbaService.findById(Long.parseLong(tumbas[1]));
                if (tumbaDifuntoService.ListaValidacionTraslado(tumbas[0]).size() < 3) {
                    //guardar en tumba 1
                    tumbaDifunto.setEstadoTumbaDifunto(true);
                    tumbaDifunto.setTumba(tumbas[0]);
                    tumbaDifunto1 = tumbaDifuntoService.save(tumbaDifunto);

                    tumba1.setEstado_Tumba("Ocupado");
                    tumbaService.save(tumba1);
                    tumba2.setEstado_Tumba("Ocupado");
                    tumbaService.save(tumba2);

                } else {
                    if(tumbaDifuntoService.ListaValidacionTraslado(tumbas[1]).size() < 3){
                        //guardamos en tumba 2 ya que tumba 1 esta llena
                        tumbaDifunto.setEstadoTumbaDifunto(true);
                        tumbaDifunto.setTumba(tumbas[1]);
                        tumbaDifunto1 = tumbaDifuntoService.save(tumbaDifunto);

                        tumba1.setEstado_Tumba("Ocupado");
                        tumbaService.save(tumba1);
                        tumba2.setEstado_Tumba("Ocupado");
                        tumbaService.save(tumba2);
                    }else{
                        //ambas llenas retorna error...
                        response.put("mensaje", "El registro no ha sido creado");
                        response.put("Registro", tumbaDifunto1);

                        return new ResponseEntity<Map<String, Object>>(response, NOT_ACCEPTABLE);
                    }

                }
            } else {
                tumbaDifunto.setEstadoTumbaDifunto(true);
                tumbaDifunto1 = tumbaDifuntoService.save(tumbaDifunto);
                Tumba tumba = tumbaService.findById(Long.parseLong(tumbaDifunto.getTumba()));
                tumba.setEstado_Tumba("Ocupado");
                tumbaService.save(tumba);
            }


        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El registro ha sido creado con éxito!");
        response.put("Registro", tumbaDifunto1);

        return new ResponseEntity<Map<String, Object>>(response, CREATED);
    }


    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/updateTumbaDifunto/{id}")
    public ResponseEntity<?> update(@RequestBody Tumba_Difunto tumbaDifunto, @PathVariable Long id) {
        Tumba_Difunto tumbaDifunto1 = tumbaDifuntoService.findOne(id).get();
        Tumba_Difunto tumbaDifunto2 = null;

        Map<String, Object> response = new HashMap<String, Object>();

        if (tumbaDifunto1 == null) {
            response.put("mensaje", "No se pudo editar, el registro con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            tumbaDifunto1.setEstadoTumbaDifunto(false);
            tumbaDifunto2 = tumbaDifuntoService.save(tumbaDifunto1);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el registro en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El registro ha sido actualizado con éxito!");
        response.put("tumbaDifunto", tumbaDifunto2);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findContratoPorDifunto/{id}")
    public Tumba_Difunto findContratoPorDifunto(@PathVariable Long id) {
        Tumba_Difunto contratoBuscado = tumbaDifuntoService.contratoPorDifunto(id);
        return contratoBuscado;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/mostrarDifunto/{id}")
    public List<Tumba_Difunto> mostrarDifunto(@PathVariable String id) {
        List<Tumba_Difunto> difuntoBuscado = tumbaDifuntoService.ListaValidacionTraslado(id);
        return difuntoBuscado;
    }

}
