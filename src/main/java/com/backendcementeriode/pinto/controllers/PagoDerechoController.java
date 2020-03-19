package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.*;
import com.backendcementeriode.pinto.models.Entity.*;
import com.backendcementeriode.pinto.models.Service.classImpl.ContratoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.DechoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.PagosDerechoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.contratov2ServiceImpl;
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
public class PagoDerechoController {

    @Autowired
    public PagosDerechoServiceImpl pagosDerechoService;
    @Autowired
    public DechoServiceImpl derechoService;

    @Autowired
    public ContratoServiceImpl contratoService;

    @Autowired
    public contratov2ServiceImpl contratov2Service;

    @RequestMapping(value = "/listPagosDerecho", method = RequestMethod.GET )
    public List<PagosDerecho> findAll() {
        List<PagosDerecho> all = pagosDerechoService.findAll();
        return all;
    }

    @PostMapping(value = "/savePagoDerecho")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody PagosDerecho pagosDerecho) {

        PagosDerecho pagosDerecho1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            pagosDerecho.setEstadoCuota_Derecho(false);
            pagosDerecho1 = this.pagosDerechoService.save(pagosDerecho);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El pago de mantención ha sido creado con éxito!");
        response.put("Pago derecho", pagosDerecho1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }

    @PutMapping(value = "/updatePagoDerecho/{id}")
    @ResponseStatus(value = CREATED)
    public  ResponseEntity<?> update(@RequestBody PagosDerecho pagosDerecho, @PathVariable Long id){
        PagosDerecho pagosDerecho1= pagosDerechoService.findById(id).get();
        PagosDerecho PagosUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(pagosDerecho1==null) {
            response.put("mensaje","No se pudo pagar la cuota con id: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            pagosDerecho1.setEstadoCuota_Derecho(true);
            PagosUpdated = pagosDerechoService.save(pagosDerecho1);
        } catch(DataAccessException e) {
            response.put("mensaje","Error al pagar la cuota");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
        response.put("mensaje","El pago ha sido actualizado con éxito!");
        response.put("Tipo Tumba",PagosUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }


    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findPD/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        PagosDerecho pagosDerecho=null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            pagosDerecho=pagosDerechoService.findById(id).get();
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(pagosDerecho==null) {
            response.put("mensaje","El pago del derecho con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(pagosDerecho,HttpStatus.OK);
    }
    void crearNuevaCuota(PagosDerecho pagosDerecho) throws ParseException {
        PagosDerecho nuevaCuota = new PagosDerecho();
        nuevaCuota.setValorCuota_Derecho(pagosDerecho.getValorCuota_Derecho());
        LocalDate fechaV = pagosDerecho.getFechaVencimiento_Derecho();
        int dia = fechaV.getDayOfMonth();
        int mes = fechaV.getMonthValue();
        int anio = fechaV.getYear();
        if (mes == 12){
            mes =1;
            anio= anio+1;
        }else{
            mes = mes+1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = anio+"-"+mes+"-"+dia;
        Date fechaV2 = sdf.parse(dateString);
        LocalDate fv3 = convertToLocalDateViaInstant(fechaV2);

        nuevaCuota.setFechaVencimiento_Derecho(fv3);
        LocalDate fechaP = pagosDerecho.getFechaPago_Derecho();
        int diaP = fechaP.getDayOfMonth();
        int mesP = fechaP.getMonthValue();
        int anioP = fechaP.getYear();
        if (mesP == 12){
            mesP = 1;
            anioP= anioP+1;
        }else{
            mesP = mesP+1;
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String dateString2 = anioP+"-"+mesP+"-"+diaP;
        Date fechaP2 = sdf2.parse(dateString2);
        LocalDate fv4 = convertToLocalDateViaInstant(fechaP2);

        nuevaCuota.setFechaPago_Derecho(fv4);
        nuevaCuota.setEstadoCuota_Derecho(false);
        nuevaCuota.setDerecho(pagosDerecho.getDerecho());
        pagosDerechoService.save(nuevaCuota);
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Secured({"ROLE_ADMIN","ROLE_CLIENT"})
    @RequestMapping(value = "/listCuotasDerecho/{id}", method = RequestMethod.GET)
    public List<PagosDerecho> findCuotasPorContratoPorIdCliente(@PathVariable Long id){
        Contrato c1 = contratoService.findOne(id).get();

        List<PagosDerecho> all = pagosDerechoService.cuotasPorIdContrato(c1.getId_contrato());

        return all;
    }

    @PostMapping(value = "/renovarCuotaDerechopor10anios/{id}")
    public ResponseEntity<?> renovarCuotasDerecho(@PathVariable Long id) {
        Contrato contrato = contratoService.findById(id);
        contratov2 contratov2 = contratov2Service.findOne(id).get();
        int valorRenovado10= 100000;
        int valCuotasRenovadas = valorRenovado10/contrato.getN_Cuotas();

        Map<String, Object> response = new HashMap<String, Object>();

        try {
            int numeroCuotas = contrato.getN_Cuotas();
            for (int i = 0; i < numeroCuotas; i++) {
               PagosDerecho pagosDerecho = new PagosDerecho();
               pagosDerecho.setDerecho(contratov2.getDerecho());
               pagosDerecho.setEstadoCuota_Derecho(false);
               pagosDerecho.setFechaPago_Derecho(null);
               pagosDerecho.setFechaVencimiento_Derecho(contrato.getFecha_Pago());
               pagosDerecho.setValorCuota_Derecho(valCuotasRenovadas);
               pagosDerechoService.save(pagosDerecho);
            }
            contrato.setPerpetuidad(10);
            contratoService.save(contrato);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El pago de derecho ha sido pagado con éxito!");

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }

    @PostMapping(value = "/renovarCuotaDerechopor20anios/{id}")
    public ResponseEntity<?> renovarCuotasDerecho2(@PathVariable Long id) {
        Contrato contrato = contratoService.findById(id);
        contratov2 contratov2 = contratov2Service.findOne(id).get();
        int valorRenovado20= 200000;
        int valCuotasRenovadas = valorRenovado20/contrato.getN_Cuotas();

        Map<String, Object> response = new HashMap<String, Object>();

        try {
            int numeroCuotas = contrato.getN_Cuotas();
            for (int i = 0; i < numeroCuotas; i++) {
                PagosDerecho pagosDerecho = new PagosDerecho();
                pagosDerecho.setDerecho(contratov2.getDerecho());
                pagosDerecho.setEstadoCuota_Derecho(false);
                pagosDerecho.setFechaPago_Derecho(null);
                pagosDerecho.setFechaVencimiento_Derecho(contrato.getFecha_Pago());
                pagosDerecho.setValorCuota_Derecho(valCuotasRenovadas);
                pagosDerechoService.save(pagosDerecho);
            }
            contrato.setPerpetuidad(20);
            contratoService.save(contrato);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El pago de derecho ha sido pagado con éxito!");

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }

}
