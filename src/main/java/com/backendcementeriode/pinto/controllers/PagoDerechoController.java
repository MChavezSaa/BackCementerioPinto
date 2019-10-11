package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.PagosDerecho;
import com.backendcementeriode.pinto.models.Service.classImpl.DechoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.PagosDerechoServiceImpl;
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

@CrossOrigin(value = "http://localhost:4200")
@RestController
public class PagoDerechoController {

    @Autowired
    public PagosDerechoServiceImpl pagosDerechoService;
    @Autowired
    public DechoServiceImpl derechoService;

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
            response.put("mensaje","No se pudo editar, el tipo de tumba con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            pagosDerecho1.setId_PagosDerecho(pagosDerecho.getId_PagosDerecho());
            pagosDerecho1.setDerecho(pagosDerecho.getDerecho());

            pagosDerecho1.setEstadoCuota_Derecho(true);
            pagosDerecho1.setFechaPago_Derecho(pagosDerecho.getFechaPago_Derecho());
            pagosDerecho1.setFechaVencimiento_Derecho(pagosDerecho.getFechaVencimiento_Derecho());
            pagosDerecho1.setValorCuota_Derecho(pagosDerecho.getValorCuota_Derecho());
            PagosUpdated = pagosDerechoService.save(pagosDerecho1);
            List<PagosDerecho> pagosTotales = pagosDerechoService.findByDerecho(pagosDerecho.getDerecho().getId_Derecho());
            for(int i =0; i<pagosTotales.size(); i++){
                if (pagosTotales.get(i).isEstadoCuota_Derecho()== true) {
                    //creamos nueva cuota
                    crearNuevaCuota(pagosDerecho);
                    break;
                }else{
                    break;
                }

            }

        }catch (ParseException ex) {
            response.put("mensaje","Error al actualizar el funcionario en la base de datos");
            response.put("error",ex.getMessage().concat(": ").concat(ex.getMessage()));
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el funcionario en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);


    }


        response.put("mensaje","El pago ha sido actualizado con éxito!");
        response.put("Tipo Tumba",PagosUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }
    void crearNuevaCuota(PagosDerecho pagosDerecho) throws ParseException {
        //se crea cuota siguiente
        PagosDerecho nuevaCuota = new PagosDerecho();
        nuevaCuota.setValorCuota_Derecho(pagosDerecho.getValorCuota_Derecho());
        /*manejo de la fecha de vencimiento*//*revisar fecha vencimiento de nueva cuota*/
        Date fechaV = pagosDerecho.getFechaVencimiento_Derecho();
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

        nuevaCuota.setFechaVencimiento_Derecho(fechaV2);
        /*manejo de la fecha de pago*/
        Date fechaP = pagosDerecho.getFechaPago_Derecho();
        int diaP = fechaP.getDay();
        int mesP = fechaP.getMonth();
        int anioP = fechaP.getYear();
        if (mesP == 12){
            mesP = 1;
            anioP= anioP+1;
        }else{
            mesP = mesP+1;
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String dateString2 = diaP+"-"+mesP+"-"+anioP;
        Date fechaP2 = sdf2.parse(dateString2);
        /*Fin manejo de fecha venciminento*/

        nuevaCuota.setFechaPago_Derecho(fechaP2);
        nuevaCuota.setEstadoCuota_Derecho(false);
        nuevaCuota.setDerecho(pagosDerecho.getDerecho());
        pagosDerechoService.save(nuevaCuota);
    }
}
