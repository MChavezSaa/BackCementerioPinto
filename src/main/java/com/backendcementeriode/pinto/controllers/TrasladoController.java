package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.TipoTumba;
import com.backendcementeriode.pinto.models.Entity.Traslado;
import com.backendcementeriode.pinto.models.Service.classImpl.DifuntoServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.TrasladoServiceImpl;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TrasladoController {

    @Autowired
    private TrasladoServiceImpl trasladoService;
    @Autowired
    private DifuntoServiceImpl difuntoService;

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
        Map<String,Object> response =new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            traslado=trasladoService.findOne(id);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if(traslado==null) {
            response.put("mensaje","El traslado con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(traslado,HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveTraslado")
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

}
