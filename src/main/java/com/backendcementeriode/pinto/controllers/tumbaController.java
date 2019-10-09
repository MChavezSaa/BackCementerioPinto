package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.Tumba;
import com.backendcementeriode.pinto.models.Service.classImpl.TumbaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class tumbaController {

    @Autowired
    public TumbaServiceImpl tumbaService;

    @RequestMapping(value = "/listTumbas", method = RequestMethod.GET )
    public List<Tumba> findAll() {
        List<Tumba> all = tumbaService.findAll();
        return all;
    }


    @PostMapping(value = "/saveTumba")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Tumba tumba) {

        Tumba tumba1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

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
    }


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
            tumba1.setId_tumba(tumba.getId_tumba());
            tumba1.setAncho(tumba.getAncho());
            tumba1.setCliente(tumba.getCliente());
            tumba1.setEstado_Tumba(tumba.getEstado_Tumba());
            tumba1.setFuncionario(tumba.getFuncionario());
            tumba1.setLargo(tumba.getLargo());
            tumba1.setOrientacion_Tumba(tumba.getOrientacion_Tumba());
            tumba1.setPatio(tumba.getPatio());
            tumba1.setTipo_Tumba(tumba.getTipo_Tumba());
            tumba1.setTumba_difunto(tumba.getTumba_difunto());
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

}
