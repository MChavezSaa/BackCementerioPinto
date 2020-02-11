package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.*;
import com.backendcementeriode.pinto.models.Service.classImpl.*;
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
import java.util.*;

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
    public List<Contrato> findAll() {
        List<Contrato> all = contratoService.findAll();
        return all;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/ListDistinct", method = RequestMethod.GET)
    public List<Object> findDistinct() {
        List<Object> all = contratoService.distincCliente();
        return all;
    }


    ////-------------- Guardar contrato ---------------------////
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveContrato")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Contrato contrato) {
        //seteamos el valor de la cuota
        contrato.setEstado_Contrato(true);
        float nC = contrato.getN_Cuotas();
        float vT = contrato.getValor_Terreno();
        float pie = contrato.getPagoInicial();
        float valCuota = (vT - pie) / nC;
        contrato.setVCuotas(valCuota);

        Contrato contrato1 = null;
        Map<String, Object> response = new HashMap<String, Object>();


        try {
            contrato.setPerpetuidad(20);
            if (contrato.getTipoTumba().getNombretipo_tumba().equalsIgnoreCase("Doble")) {
                String tumba[] = contrato.getTumba().split("-");
                Tumba tumba1 = tumbaService.findById(Integer.parseInt(tumba[0]));
                Tumba tumba2 = tumbaService.findById(Integer.parseInt(tumba[1]));
                tumba1.setAncho(3.4F);
                tumba1.setLargo(5.0F);
                tumba1.setEstado_Tumba("Reservado");


                tumba2.setAncho(3.4F);
                tumba2.setLargo(5.0F);
                tumba2.setEstado_Tumba("Reservado");


                tumbaService.save(tumba1);
                tumbaService.save(tumba2);

            } else {
                if (contrato.getTipoTumba().getNombretipo_tumba().equalsIgnoreCase("Simple") ) {
                    Tumba tumba1 = tumbaService.findById(Long.parseLong(contrato.getTumba()));
                    tumba1.setAncho(1.7F);
                    tumba1.setLargo(2.5F);
                    tumba1.setEstado_Tumba("Reservado");
                    tumbaService.save(tumba1);


                } else {
                    if (contrato.getTipoTumba().getNombretipo_tumba().equalsIgnoreCase("Nicho")) {
                        Tumba tumba1 = tumbaService.findById(Long.parseLong(contrato.getTumba()));
                        tumba1.setAncho(0.9F);
                        tumba1.setLargo(2.7F);
                        tumba1.setEstado_Tumba("Reservado");
                        tumbaService.save(tumba1);


                    } else {
                        if (contrato.getTipoTumba().getNombretipo_tumba().equalsIgnoreCase("Mausoleos")) {
                            Tumba tumba1 = tumbaService.findById(Long.parseLong(contrato.getTumba()));
                            tumba1.setAncho(4F);
                            tumba1.setLargo(3F);
                            tumba1.setEstado_Tumba("Reservado");
                            tumbaService.save(tumba1);

                        }
                    }
                }
            }
            contrato1 = contratoService.save(contrato);
            /*CREAMOS DERECHO*/
            Derecho derecho = new Derecho();
            derecho.setFecha_Inscripcion_Derecho(contrato.getFecha_Ingreso_Venta());
            derecho.setFecha_Pago_Derecho(contrato.getFecha_Pago());
            derecho.setFecha_Vencimiento_Derecho(crearFechaVencimientoDerecho(contrato.getFecha_Ingreso_Venta()));
            derecho.setValor_Cuota_Derecho(contrato.getVCuotas());
            derecho.setNumero_Cuotas_Derecho((int) nC);
            derecho.setEstadoDerecho(true);
            derecho.setContrato(contrato);
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
            for (int i = 0; i < (int) nC; i++) {
                PagosDerecho pagosDerecho = new PagosDerecho();
                //fecha de pago derecho alusion a cuando pago la persona.
                pagosDerecho.setFechaPago_Derecho(null);
                //fecha estimada de pago
                pagosDerecho.setFechaVencimiento_Derecho(fechaVencimientoPD(i + 1,
                        contrato.getFecha_Pago()));
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
            cuotasMantencion.setContrato(contrato);
            //guardamos cuota
            cuotasMantencionService.save(cuotasMantencion);

            /*CREAMOS LOS REGISTROS PARA LOS PAGOS DE CM*/
            for (int i = 0; i < 12; i++) {

                // Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                PagosMantencion pagosMantencion = new PagosMantencion();
                //inicia null por que no se ha pagado
                pagosMantencion.setFechaPago_Mantencion(null);
                //fecha estimada de pago de mantencion (mes+1)
                pagosMantencion.setFechaVencimiento_Mantencion(
                        fechaVencimientoPD(i + 1,
                                contrato.getFecha_Ingreso_Venta()));
                pagosMantencion.setValorCuota_Mantencion(500);
                //false no pago true pago...
                pagosMantencion.setEstadoCuota_Mantencion(false);
                pagosMantencion.setCuotasMantencion(cuotasMantencion);
                //se guarda el registro
                pagosMantencionService.save(pagosMantencion);

            }


        } catch (DataAccessException | ParseException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El contrato ha sido creado con éxito!");
        response.put("Contrato", contrato1);

        return new ResponseEntity<Map<String, Object>>(response, OK);

    }


    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/updateContrato/{id}")
    public ResponseEntity<?> update(@RequestBody Contrato contrato, @PathVariable Long id) {
        Contrato contrato1 = contratoService.findById(id);
        Contrato contrato2 = null;

        Map<String, Object> response = new HashMap<String, Object>();

        if (contrato1 == null) {
            response.put("mensaje", "No se pudo editar, el contrato con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            contrato1.setFecha_Pago(contrato.getFecha_Pago());
            contrato1.setCliente(contrato.getCliente());
            contrato1.setMedio_Pago(contrato.getMedio_Pago());
            contrato2 = contratoService.save(contrato1);

            //llamar al service de tumbadifunto para poder generar el "entierro del muertito"
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el contrato en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La contrato ha sido actualizado con éxito!");
        response.put("Contrato", contrato2);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findContrato/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Contrato contrato = null;
        Map<String, Object> response = new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            contrato = contratoService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if (contrato == null) {
            response.put("mensaje", "El contrato con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(contrato, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/DeleteContrato/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Contrato contratoB = contratoService.findById(id);
        Contrato contrato2 = null;

        Map<String, Object> response = new HashMap<String, Object>();
        try {
            contratoB.setEstado_Contrato(false);
            contrato2= contratoService.save(contratoB);
            if(contratoB.getTipoTumba().getNombretipo_tumba().equalsIgnoreCase("Doble")){
                String tumbas[] = contratoB.getTumba().split("-");
                Tumba tumba1 = tumbaService.findById(Long.parseLong(tumbas[0]));
                Tumba tumba2 = tumbaService.findById(Long.parseLong(tumbas[1]));
                tumba1.setEstado_Tumba("Disponible");
                tumba2.setEstado_Tumba("Disponible");

                tumbaService.save(tumba1);
                tumbaService.save(tumba2);
            }else{
                Tumba tumba = tumbaService.findById(Long.parseLong(contratoB.getTumba()));
                tumba.setEstado_Tumba("Disponible");
                tumbaService.save(tumba);
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al deshabilitar el contrato de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El contrato fue deshabilitado con éxito!");
        response.put("Contrato: ", contrato2);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }



    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/CambiarEstadoContrato/{id}")
    public ResponseEntity<?> darAlta(@RequestBody Contrato contrato, @PathVariable Long id) {
        Contrato contratoActual = contratoService.findById(id);
        contratoActual.setEstado_Contrato(true);
        Contrato contratoUpdate = null;

        Map<String, Object> response = new HashMap<String, Object>();

        if (contratoActual == null) {
            response.put("mensaje", "No se pudo cambiar el estado del contrato con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            contratoUpdate = contratoService.save(contratoActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al cambiar el estado del contrato en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El contrato ha cambiado su estado con éxito!");
        response.put("Contrato", contratoUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }


    private LocalDate fechaVencimientoPD(int numeroCuota, LocalDate fecha) throws ParseException {
        Date d1 = convertToDateViaSqlDate(fecha);
        int dia = d1.getDay();
        int mes = d1.getMonth();
        int anio = fecha.getYear();

        if (mes + numeroCuota < 13) {
            if (mes + numeroCuota == 12) {
                mes = mes + numeroCuota;
                anio = anio + 1;
                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                String dateString3 = anio + "-" + mes + "-" + dia;
                Date fechaVencimientoCM3 = sdf3.parse(dateString3);
                LocalDate fecha1 = convertToLocalDateViaInstant(fechaVencimientoCM3);
                return fecha1;
            } else {
                mes = mes + 1;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = anio + "-" + mes + "-" + dia;
                Date fechaVencimientoCM = sdf.parse(dateString);
                LocalDate fecha2 = convertToLocalDateViaInstant(fechaVencimientoCM);
                return fecha2;
            }
        } else {
            int aux = (mes + numeroCuota) - 12;
            mes = aux;
            anio = anio + 1;
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String dateString2 = anio + "-" + mes + "-" + dia;
            Date fechaVencimientoCM2 = sdf2.parse(dateString2);
            LocalDate fecha3 = convertToLocalDateViaInstant(fechaVencimientoCM2);
            return fecha3;
        }
    }

    private LocalDate crearFechaVencimientoCM(LocalDate fechaIngreso) throws ParseException {
        Date d1 = convertToDateViaSqlDate(fechaIngreso);
        int dia = d1.getDay();
        int mes = d1.getMonth();
        int anio = fechaIngreso.getYear();
        if (mes == 12) {
            mes = 1;
            anio = anio + 1;
        } else {
            mes = mes + 1;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = anio + "-" + mes + "-" + dia;
        Date fechaVencimientoCM = sdf.parse(dateString);
        LocalDate fecha1 = convertToLocalDateViaInstant(fechaVencimientoCM);
        return fecha1;


    }

    private LocalDate crearFechaVencimientoDerecho(LocalDate fechaIngreso) throws ParseException {
        Date d1 = convertToDateViaSqlDate(fechaIngreso);
        int dia = d1.getDay();
        int mes = d1.getMonth();
        int anio = fechaIngreso.getYear();
        if (mes == 12) {
            mes = 1;
            anio = anio + 20;
        } else {
            mes = mes + 1;
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
