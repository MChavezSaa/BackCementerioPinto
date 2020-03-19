package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Contrato;
import com.backendcementeriode.pinto.models.Entity.CuotasMantencion;
import com.backendcementeriode.pinto.models.Entity.PagosMantencion;
import com.backendcementeriode.pinto.models.Service.classImpl.ContratoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.CuotasMantencionServiceImpl;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;


@CrossOrigin(origins = "*")
@RestController
public class PagoMantencionController {

    @Autowired
    public PagosMantencionServiceImpl pagosMantencionService;
    @Autowired
    public ContratoServiceImpl contratoService;
    @Autowired
    public CuotasMantencionServiceImpl cuotasMantencionService;

    @RequestMapping(value = "/listPagosMantencion", method = RequestMethod.GET)
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
    public ResponseEntity<?> update(@RequestBody PagosMantencion pagosMantencion, @PathVariable Long id) {
        PagosMantencion PagosActual = pagosMantencionService.findById(id).get();
        PagosMantencion PagosUpdated = null;

        Map<String, Object> response = new HashMap<String, Object>();

        if (PagosActual == null) {
            response.put("mensaje", "No se pudo editar, el pago con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }


        if (id.intValue() % 12 == 0) {
            System.out.println("paso al if del mod");

            PagosActual.setFechaPago_Mantencion(LocalDate.now());
            PagosActual.setEstadoCuota_Mantencion(true);
            PagosUpdated = pagosMantencionService.save(PagosActual);
            System.out.println("paso pagoservice");

            CuotasMantencion cuotasMantencion = cuotasMantencionService.findById(pagosMantencion.getCuotasMantencion()
                    .getId_Cuotas_Mantencion()).get();

            cuotasMantencion.setEstadoCM(true);
            System.out.println(cuotasMantencion.toString());
            cuotasMantencionService.delete(cuotasMantencion);
            System.out.println("paso cuotaservice");



        } else {
            PagosActual.setEstadoCuota_Mantencion(true);
            PagosActual.setFechaPago_Mantencion(LocalDate.now());
            PagosUpdated = pagosMantencionService.save(PagosActual);
        }


        response.put("mensaje", "El pago ha sido actualizado con éxito!");
        response.put("Pago Mantención", PagosUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findPM/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        PagosMantencion pagosMantencion = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            pagosMantencion = pagosMantencionService.findById(id).get();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pagosMantencion == null) {
            response.put("mensaje", "El pago de mantencion con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(pagosMantencion, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN","ROLE_CLIENT"})
    @RequestMapping(value = "/listCuotasPorIDClienteEnContrato/{id}", method = RequestMethod.GET)
    public List<PagosMantencion> findCuotasPorContratoPorIdCliente(@PathVariable Long id) {
        Contrato c1 = contratoService.findOne(id).get();

        List<PagosMantencion> all = pagosMantencionService.cuotasPorIdClienteEnContrato(c1.getId_contrato());
        return all;
    }

    @Secured("ROLE_ADMIN")
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

    @PostMapping(value = "/renovarCuotaMantencion/{id}")
    public ResponseEntity<?> renovarCuotaMantencion(@PathVariable Long id) {
        Contrato contrato = contratoService.findById(id);
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            CuotasMantencion cuotasMantencion = new CuotasMantencion();
            cuotasMantencion.setContrato(contrato);
            cuotasMantencion.setEstadoCM(false);
            cuotasMantencion.setFecha_Vencimiento_CM(crearFechaVencimientoCM(contrato.getFecha_Pago()));
            cuotasMantencion.setFecha_Pago_CM(null);
            cuotasMantencion.setNumero_Cuotas_CM(12);
            cuotasMantencion.setValor_Cuota_CM(500);
            cuotasMantencionService.save(cuotasMantencion);

            for (int i = 0; i < 12; i++) {
                PagosMantencion pagosMantencion = new PagosMantencion();
                pagosMantencion.setFechaPago_Mantencion(null);
                pagosMantencion.setEstadoCuota_Mantencion(false);
                pagosMantencion.setCuotasMantencion(cuotasMantencion);
                pagosMantencion.setFechaVencimiento_Mantencion(crearFechaVencimientoCM(contrato.getFecha_Pago()));
                pagosMantencion.setValorCuota_Mantencion(500);
                pagosMantencionService.save(pagosMantencion);
            }

        } catch (DataAccessException | ParseException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El pago de mantención ha sido pagado con éxito!");

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }


    private LocalDate crearFechaVencimientoCM(LocalDate fechaIngreso) throws ParseException {
        Date d1 = convertToDateViaSqlDate(fechaIngreso);
        int dia = d1.getDay();
        int mes = d1.getMonth();
        int anio = fechaIngreso.getYear();
        String mesString=mes+"";
        if (mesString.equalsIgnoreCase("12")) {
            mes = 1;
            anio++;
        } else {
            mes++;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = anio + "-" + mes + "-" + dia;
        Date fechaVencimientoCM = sdf.parse(dateString);
        LocalDate fecha1 = convertToLocalDateViaInstant(fechaVencimientoCM);
        return fecha1;


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
