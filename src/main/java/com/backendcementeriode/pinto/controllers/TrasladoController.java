package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.Traslado;
import com.backendcementeriode.pinto.models.Entity.Tumba;
import com.backendcementeriode.pinto.models.Entity.Tumba_Difunto;
import com.backendcementeriode.pinto.models.Entity.Varios.traslado3;
import com.backendcementeriode.pinto.models.Service.classImpl.DifuntoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.TrasladoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.TumbaDifuntoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.TumbaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "*")
@RestController
public class TrasladoController {

    @Autowired
    private TrasladoServiceImpl trasladoService;
    @Autowired
    private DifuntoServiceImpl difuntoService;
    @Autowired
    private TumbaDifuntoServiceImpl tumbaDifuntoService;

    @Autowired
    private TumbaServiceImpl tumbaService;



    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listTraslados", method = RequestMethod.GET )
    public List<Traslado> findAll() {
        List<Traslado> all = trasladoService.findAll();
        return all;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findTraslado/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Traslado traslado=null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            traslado=trasladoService.findOne(id);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(traslado==null) {
            response.put("mensaje","El traslado con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(traslado,HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveTrasladoExterno")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Traslado traslado) {
        Traslado traslado1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {

            difuntoService.save(traslado.getDifunto());
            traslado1 = this.trasladoService.save(traslado);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El traslado ha sido creado con éxito!");
        response.put("Traslado", traslado1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveTrasladoInterno")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> createTraslado(@RequestBody traslado3 traslado) {
        Traslado traslado1 = null;
        Traslado trasladoConstruido = new Traslado();
        Tumba_Difunto tumba_Difunto_Anterior = tumbaDifuntoService.prueba
                (traslado.getDifunto().getId_Difunto());

        Map<String, Object> response = new HashMap<String, Object>();

        try {
            List<Tumba_Difunto> validacion = tumbaDifuntoService.ListaValidacionTraslado(
                    tumba_Difunto_Anterior.getTumba());

            List<Tumba_Difunto>validacionAux = new ArrayList<>();

            for (int i = 0; i < validacion.size() ; i++) {
                if(validacion.get(i).isEstadoTumbaDifunto()== true
                        &&
                        traslado.getDifunto().getId_Difunto() !=
                                validacion.get(i).getDifunto().getId_Difunto()){
                    validacionAux.add(validacion.get(i));
                }
            }


            if (validacionAux.size()!=0){
                System.out.println("ENTRO != 0");
                trasladoConstruido.setDifunto(traslado.getDifunto());
                trasladoConstruido.setDireccion_Solicitante(traslado.getDireccion_Solicitante());
                trasladoConstruido.setFecha_Traslado(traslado.getFecha_Traslado());
                trasladoConstruido.setLugarnuevo(traslado.getLugarnuevo().getTumba());
                trasladoConstruido.setNombreC_Solicitante(traslado.getLugarnuevo().getCliente().getNombres_Cliente()
                        +" "+ traslado.getLugarnuevo().getCliente().getApellidoP_Cliente()+" "+
                        traslado.getLugarnuevo().getCliente().getApellidoM_Cliente());
                trasladoConstruido.setLugarviejo(traslado.getLugarviejo());
                trasladoConstruido.setObservaciones(traslado.getObservaciones());
                trasladoConstruido.setRut_Solicitante(traslado.getLugarnuevo().getCliente().getRut_Cliente());
                traslado.setTipoDeCambio(traslado.getTipoDeCambio());

                Tumba_Difunto tumbaDifunto1 = new Tumba_Difunto();
                tumbaDifunto1.setDifunto(traslado.getDifunto());
                tumbaDifunto1.setContrato(traslado.getLugarnuevo());
                tumbaDifunto1.setTumba(traslado.getLugarnuevo().getTumba());
                tumbaDifunto1.setFecha_Entierro_TD(traslado.getFecha_Traslado());
                tumbaDifuntoService.save(tumbaDifunto1);

                Tumba tumba = tumbaService.findById(Long.parseLong(tumba_Difunto_Anterior.getTumba()));
                tumba.setEstado_Tumba("Ocupado");
                tumbaService.save(tumba);
                Tumba tumba2 = tumbaService.findById(Long.parseLong(traslado.getLugarnuevo().getTumba()));
                tumba2.setEstado_Tumba("Ocupado");
                tumbaService.save(tumba2);

                traslado1= trasladoService.save(trasladoConstruido);

            }else{

                trasladoConstruido.setDifunto(traslado.getDifunto());
                trasladoConstruido.setDireccion_Solicitante(traslado.getDireccion_Solicitante());
                trasladoConstruido.setFecha_Traslado(traslado.getFecha_Traslado());
                trasladoConstruido.setLugarnuevo(traslado.getLugarnuevo().getTumba());
                trasladoConstruido.setNombreC_Solicitante(traslado.getLugarnuevo().getCliente().getNombres_Cliente()
                        +" "+ traslado.getLugarnuevo().getCliente().getApellidoP_Cliente()+" "+
                        traslado.getLugarnuevo().getCliente().getApellidoM_Cliente());
                trasladoConstruido.setLugarviejo(traslado.getLugarviejo());
                trasladoConstruido.setObservaciones(traslado.getObservaciones());
                trasladoConstruido.setRut_Solicitante(traslado.getLugarnuevo().getCliente().getRut_Cliente());
                traslado.setTipoDeCambio(traslado.getTipoDeCambio());

                Tumba_Difunto tumbaDifunto1 = new Tumba_Difunto();
                tumbaDifunto1.setDifunto(traslado.getDifunto());
                tumbaDifunto1.setContrato(traslado.getLugarnuevo());
                tumbaDifunto1.setTumba(traslado.getLugarnuevo().getTumba());
                tumbaDifunto1.setFecha_Entierro_TD(traslado.getFecha_Traslado());
                tumbaDifunto1.setEstadoTumbaDifunto(true);
                tumbaDifuntoService.save(tumbaDifunto1);
                Tumba tumba2 = tumbaService.findById(Long.parseLong(traslado.getLugarnuevo().getTumba()));
                tumba2.setEstado_Tumba("Ocupado");
                tumbaService.save(tumba2);

                Tumba tumba = tumbaService.findById(Long.parseLong(tumba_Difunto_Anterior.getTumba()));
                tumba.setEstado_Tumba("Reservado");
                tumbaService.save(tumba);

                tumba_Difunto_Anterior.setEstadoTumbaDifunto(false);
                tumbaDifuntoService.save(tumba_Difunto_Anterior);

                traslado1= trasladoService.save(trasladoConstruido);
            }


        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El traslado ha sido creado con éxito!");
        response.put("Traslado", traslado1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }


}
