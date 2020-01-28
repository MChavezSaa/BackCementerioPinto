package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.*;
import com.backendcementeriode.pinto.models.Service.classImpl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ContratoController {
    @Autowired
    private ContratoServiceImpl contratoService;
    @Autowired
    private DechoServiceImpl derechoService;
    @Autowired
    private PagosDerechoServiceImpl pagosDerechoService;
    /*CUOTAS MANTENCION*/
    @Autowired
    private CuotasMantencionServiceImpl cuotasMantencionService;
    @Autowired
    private PagosMantencionServiceImpl pagosMantencionService;

    /*Arreglucho*/
    @Autowired
    private contratov2ServiceImpl contratov2Service;

    @Autowired
    private TumbaServiceImpl tumbaService;


    ////-------------- Listar Clientes ---------------------////
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listContratos", method = RequestMethod.GET)
    public List<Contrato> findAll(){
        List<Contrato> all = contratoService.findAll();
        return all;
    }


    ////-------------- Guardar contrato ---------------------////
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveContrato")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Contrato contrato){
        //seteamos el valor de la cuota
        float nC = contrato.getN_Cuotas();
        float vT = contrato.getValor_Terreno();
        float pie = contrato.getPagoInicial();
        float valCuota = (vT-pie)/nC;
        contrato.setVCuotas(valCuota);

        Contrato contrato1= null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            contrato1= contratoService.save(contrato);
            /*CREAMOS DERECHO*/
            Derecho derecho = new Derecho();
            derecho.setFecha_Inscripcion_Derecho(contrato.getFecha_Ingreso_Venta());
            derecho.setFecha_Pago_Derecho(contrato.getFecha_Pago());
            derecho.setFecha_Vencimiento_Derecho(crearFechaVencimientoDerecho(contrato.getFecha_Ingreso_Venta()));
            derecho.setValor_Cuota_Derecho(contrato.getVCuotas());
            derecho.setNumero_Cuotas_Derecho((int)nC);
            derecho.setEstadoDerecho(true);
            derecho.setCliente(contrato.getCliente());
            derecho.setMedioPago_Derecho(contrato.getMedio_Pago());
            derechoService.save(derecho);

            contratov2 c2 = new contratov2();
            c2.setCementerio(contrato.getCementerio());
            c2.setCliente(contrato.getCliente());
            c2.setDerecho(derecho);
            c2.setFecha_Ingreso_Venta(contrato.getFecha_Ingreso_Venta());
            c2.setFecha_Pago(contrato.getFecha_Pago());
            c2.setFuncionario(contrato.getFuncionario());
            c2.setMedio_Pago(contrato.getMedio_Pago());
            c2.setN_Cuotas(contrato.getN_Cuotas());
            c2.setPagoInicial(contrato.getPagoInicial());
            c2.setPatio(contrato.getPatio());
            c2.setTerreno(contrato.getTerreno());
            c2.setValor_Terreno(contrato.getValor_Terreno());
            c2.setTipoTumba(contrato.getTipoTumba());
            c2.setTumba(contrato.getTumba());
            c2.setVCuotas(contrato.getVCuotas());
            contratov2Service.save(c2);

            /*CREAMOS REGISTRO PARA PAGOS DE DERECHO*/
            for (int i = 0; i <(int)nC ; i++) {
                PagosDerecho pagosDerecho = new PagosDerecho();
                //fecha de pago derecho alusion a cuando pago la persona.
                pagosDerecho.setFechaPago_Derecho(null);
                //fecha estimada de pago
                pagosDerecho.setFechaVencimiento_Derecho(fechaVencimientoPD(i+1,
                        contrato.getFecha_Ingreso_Venta()));
                //valor de la cuota del derecho viene del calculo anterior
                pagosDerecho.setValorCuota_Derecho(derecho.getValor_Cuota_Derecho());
                //false para sin pagar true pagado
                pagosDerecho.setEstadoCuota_Derecho(false);
                pagosDerecho.setDerecho(derecho);
                pagosDerechoService.save(pagosDerecho);


            }

            /*CREAMOS LAS CUOTAS MANTENCION*/
            CuotasMantencion cuotasMantencion = new CuotasMantencion();
            //revisar como va fecha pago cm
            cuotasMantencion.setFecha_Pago_CM(null);
            //fecha estimada de termino de pago (anio +1)
            cuotasMantencion.setFecha_Vencimiento_CM(crearFechaVencimientoCM(contrato.getFecha_Ingreso_Venta()));
            cuotasMantencion.setNumero_Cuotas_CM(12);
            cuotasMantencion.setValor_Cuota_CM(500);
            cuotasMantencion.setCliente(contrato.getCliente());
            //guardamos cuota
            cuotasMantencionService.save(cuotasMantencion);

            /*CREAMOS LOS REGISTROS PARA LOS PAGOS DE CM*/
            for (int i = 0; i <12 ; i++) {
                PagosMantencion pagosMantencion = new PagosMantencion();
                //inicia null por que no se ha pagado
                pagosMantencion.setFechaPago_Mantencion(null);
                //fecha estimada de pago de mantencion (mes+1)
                pagosMantencion.setFechaVencimiento_Mantencion(
                        fechaVencimientoPD(i+1,
                        contrato.getFecha_Ingreso_Venta()));
                pagosMantencion.setValorCuota_Mantencion(500);
                //false no pago true pago...
                pagosMantencion.setEstadoCuota_Mantencion(false);
                pagosMantencion.setCuotasMantencion(cuotasMantencion);
                //se guarda el registro
                pagosMantencionService.save(pagosMantencion);

            }
            //creamos arregluchox
            Tumba tumba = contrato.getTumba();
            tumba.setEstado_Tumba("Reservado");
            tumba.setTipo_Tumba(contrato.getTipoTumba());
            tumbaService.save(tumba);


        }catch(DataAccessException | ParseException e) {
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El contrato ha sido creado con éxito!");
        response.put("Contrato",contrato1);

        return new ResponseEntity<Map<String,Object>>(response, OK);

    }


    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/updateContrato/{id}")
    public ResponseEntity<?> update(@RequestBody Contrato contrato, @PathVariable Long id) {
        Contrato contrato1=contratoService.findOne(id).get();
        Contrato contrato2=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(contrato1==null) {
            response.put("mensaje","No se pudo editar, el contrato con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            contrato1.setId_contrato(contrato.getId_contrato());
            contrato1.setCementerio(contrato.getCementerio());
            contrato1.setTerreno(contrato.getTerreno());
            contrato1.setPatio(contrato.getPatio());
            contrato1.setTumba(contrato.getTumba());
            contrato1.setTipoTumba(contrato.getTipoTumba());
           // contrato1.setDerecho(contrato.getDerecho());
            contrato1.setCliente(contrato.getCliente());
           // contrato1.setPagosDerecho(contrato.getPagosDerecho());

            contrato2=contratoService.save(contrato1);
            //llamar al service de tumbadifunto para poder generar el "entierro del muertito"
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el contrato en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","La contrato ha sido actualizado con éxito!");
        response.put("Contrato",contrato2);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findContrato/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Contrato contrato=null;
        Map<String,Object> response =new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            contrato=contratoService.findOne(id).get();
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if(contrato==null) {
            response.put("mensaje","El contrato con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(contrato,HttpStatus.OK);
    }


    private Date fechaVencimientoPD(int numeroCuota, Date fecha) throws ParseException {
        int dia = fecha.getDay();
        int mes = fecha.getMonth();
        int anio = fecha.getYear();
        if(mes+numeroCuota < 13){
            if(mes+numeroCuota == 12){
                mes= 1;
                anio=anio+1;
                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                String dateString3 = anio+"-"+mes+"-"+dia;
                Date fechaVencimientoCM3 = sdf3.parse(dateString3);
                return fechaVencimientoCM3;
            }else {
                mes = mes + 1;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = anio + "-" + mes + "-" + dia;
                Date fechaVencimientoCM = sdf.parse(dateString);
                return fechaVencimientoCM;
            }
        }else{
            int aux = (mes+numeroCuota)-12;
            mes = aux;
            anio = anio+1;
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String dateString2 = anio+"-"+mes+"-"+dia;
            Date fechaVencimientoCM2 = sdf2.parse(dateString2);
            return fechaVencimientoCM2;
        }
    }


    private Date crearFechaVencimientoCM(Date fechaIngreso) throws ParseException {
        int dia = fechaIngreso.getDay();
        int mes = fechaIngreso.getMonth();
        int anio = fechaIngreso.getYear();
        anio = anio+1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = anio+"-"+mes+"-"+dia;
        Date fechaVencimientoCM = sdf.parse(dateString);
        return fechaVencimientoCM;
    }

    private Date crearFechaVencimientoDerecho(Date fechaIngreso) throws ParseException {
        int dia = fechaIngreso.getDay();
        int mes = fechaIngreso.getMonth();
        int anio = fechaIngreso.getYear();
        anio = anio+20;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = anio+"-"+mes+"-"+dia;
        Date fechaVencimientoD = sdf.parse(dateString);
        return fechaVencimientoD;
    }


}
