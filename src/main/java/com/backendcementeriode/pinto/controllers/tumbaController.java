package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.Funcionario;
import com.backendcementeriode.pinto.models.Entity.Tumba;
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
public class    tumbaController {

    @Autowired
    public TumbaServiceImpl tumbaService;

    @RequestMapping(value = "/listTumbas", method = RequestMethod.GET )
    public List<Tumba> findAll() {
        List<Tumba> all = tumbaService.findAll();
        return all;
    }

    @RequestMapping(value = "/listFreeTumbas", method = RequestMethod.GET )
    public List<Tumba> findFreeTumbs() {
        List<Tumba> all = tumbaService.findFreetumbs();
        return all;
    }

    @RequestMapping(value = "/listOcupadoTumbas", method = RequestMethod.GET )
    public List<Tumba> findOcupadoTumbs() {
        List<Tumba> all = tumbaService.findOcupadotumbs();
        return all;
    }

    @RequestMapping(value = "/listReservadoTumbas", method = RequestMethod.GET )
    public List<Tumba> findReservadoTumbs() {
        List<Tumba> all = tumbaService.findReservadotumbs();
        return all;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveTumbas")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Tumba tumba) {
        if(tumba!= null){

            tumba.setEstado_Tumba("Disponible");
            Tumba tumba1;
            Map<String, Object> response = new HashMap<>();

            try {

                tumba1 = tumbaService.save(tumba);
            } catch (DataAccessException e) {
                response.put("mensaje", "Error al realizar el insert en la base de datos");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
            }

            response.put("mensaje", "La tumba ha sido creado con éxito!");
            response.put("Funcionario", tumba1);

            return new ResponseEntity<Map<String, Object>>(response, OK);
        }else{
            Map<String, Object> response = new HashMap<String, Object>();

            response.put("Mensaje", "tumba nula");
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

    }


    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/updateTumba/{id}")
    public ResponseEntity<?> update(@RequestBody Tumba tumba, @PathVariable Long id) {
        Tumba tumba1=tumbaService.findById(id);
        Tumba tumba2=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(tumba1==null) {
            response.put("mensaje","No se pudo editar, el tumba con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
           // tumba1.setId_tumba(tumba.getId_tumba());
            tumba1.setNumero_Tumba(tumba.getNumero_Tumba());
            tumba1.setAncho(tumba.getAncho());
            tumba1.setCliente(tumba.getCliente());
            tumba1.setEstado_Tumba(tumba.getEstado_Tumba());
            //tumba1.setFuncionario(tumba.getFuncionario());
            tumba1.setLargo(tumba.getLargo());
            tumba1.setOrientacion_Tumba(tumba.getOrientacion_Tumba());
            tumba1.setPatio(tumba.getPatio());
            tumba1.setTipo_Tumba(tumba.getTipo_Tumba());
            tumba1.setValor_Tumba(tumba.getValor_Tumba());
            tumba2=tumbaService.save(tumba1);
            //llamar al service de tumbadifunto para poder generar el "entierro del muertito"
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar la tumba en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","La tumba ha sido actualizado con éxito!");
        response.put("tumba",tumba2);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findTumba/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Tumba tumba=null;
        Map<String,Object> response =new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            tumba=tumbaService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if(tumba==null) {
            response.put("mensaje","La tumba con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(tumba,HttpStatus.OK);
    }


}
