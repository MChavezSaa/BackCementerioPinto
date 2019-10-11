package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.PagosMantencion;
import com.backendcementeriode.pinto.models.Entity.TipoTumba;
import com.backendcementeriode.pinto.models.Service.classImpl.PagosMantencionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            response.put("mensaje","No se pudo editar, el tipo de tumba con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            PagosActual.setId_PagosMantencion(pagosMantencion.getId_PagosMantencion());
            PagosActual.setCuotasMantencion(pagosMantencion.getCuotasMantencion());
            PagosActual.setEstadoCuota_Mantencion(true);
            PagosActual.setFechaPago_Mantencion(pagosMantencion.getFechaPago_Mantencion());
            /*manejo de la fecha de vencimiento*/
            Date fechaV = pagosMantencion.getFechaVencimiento_Mantencion();
            int dia = fechaV.getDay();
            int mes = fechaV.getMonth();
            int anio = fechaV.getYear();
            if (mes == 12){
                mes =1;
                anio= anio+1;
            }else{
                mes = mes+1;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dia+"-"+mes+"-"+anio;
            Date fechaV2 = sdf.parse(dateString);
            /*Fin manejo de fecha venciminento*/

            PagosActual.setFechaVencimiento_Mantencion(fechaV2);
            PagosActual.setValorCuota_Mantencion(pagosMantencion.getValorCuota_Mantencion());
            PagosUpdated= pagosMantencionService.save(PagosActual);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el pago en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (ParseException i){
            response.put("mensaje","Error al actualizar el pago en la base de datos");
            response.put("error",i.getMessage().concat(": ").concat(i.getMessage()));

        }


        response.put("mensaje","El pago ha sido actualizado con éxito!");
        response.put("Pago Mantención",PagosUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }



}
