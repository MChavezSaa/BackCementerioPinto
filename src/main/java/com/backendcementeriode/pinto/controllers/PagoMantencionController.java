package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.Funcionario;
import com.backendcementeriode.pinto.models.Entity.PagosMantencion;
import com.backendcementeriode.pinto.models.Entity.TipoTumba;
import com.backendcementeriode.pinto.models.Service.classImpl.ContratoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.PagosMantencionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;


@CrossOrigin(value = "http://localhost:4200")
@RestController
public class PagoMantencionController {

    @Autowired
    public PagosMantencionServiceImpl pagosMantencionService;
    @Autowired
    public ContratoServiceImpl contratoService;

    @RequestMapping(value = "/listPagosMantencion", method = RequestMethod.GET )
    public List<PagosMantencion> findAll() {
        List<PagosMantencion> all = pagosMantencionService.findAll();
        return all;
    }

    @PostMapping(value = "/savePagoMantencion")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody PagosMantencion pagosMantencion) {

        PagosMantencion pagosMantencion1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            pagosMantencion.setEstadoCuota_Mantencion(false);
            pagosMantencion1 = this.pagosMantencionService.save(pagosMantencion);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El pago de mantención ha sido creado con éxito!");
        response.put("Pago de mantención", pagosMantencion1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }

    @PutMapping(value = "/updatePagoMantencion/{id}")
    @ResponseStatus(value = CREATED)
    public  ResponseEntity<?> update(@RequestBody PagosMantencion pagosMantencion, @PathVariable Long id){
        PagosMantencion PagosActual= pagosMantencionService.findById(id).get();
        PagosMantencion PagosUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(PagosActual==null) {
            response.put("mensaje","No se pudo editar, el pago con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            PagosActual.setEstadoCuota_Mantencion(true);
            PagosUpdated= pagosMantencionService.save(PagosActual);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el pago en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }


        response.put("mensaje","El pago ha sido actualizado con éxito!");
        response.put("Pago Mantención",PagosUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findPM/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        PagosMantencion pagosMantencion=null;
        Map<String,Object> response =new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            pagosMantencion=pagosMantencionService.findById(id).get();
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if(pagosMantencion==null) {
            response.put("mensaje","El pago de mantencion con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(pagosMantencion,HttpStatus.OK);
    }

    /*------------------------------------*/
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listCuotasPorIDClienteEnContrato/{id}", method = RequestMethod.GET)
    public List<PagosMantencion> findCuotasPorContratoPorIdCliente(@PathVariable Long id){
        Contrato c1 = contratoService.findOne(id).get();

        List<PagosMantencion> all = pagosMantencionService.cuotasPorIdClienteEnContrato(c1.getId_contrato());
        return all;
    }
    /*------------------------------------*/

    @PutMapping(value = "/pagarPM/{id}")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> pagarCuota(@PathVariable Long id) {
        PagosMantencion pagoPagado = pagosMantencionService.findById(id).get();
        pagoPagado.setEstadoCuota_Mantencion(true);
        PagosMantencion pagosMantencion1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            pagosMantencion1 = this.pagosMantencionService.save(pagoPagado);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El pago de mantención ha sido pagado con éxito!");
        response.put("Pago de mantención", pagosMantencion1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

}
