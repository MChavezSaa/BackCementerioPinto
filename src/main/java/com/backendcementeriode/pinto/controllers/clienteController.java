package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Cliente;
import com.backendcementeriode.pinto.models.Service.classImpl.ClienteServiceImpl;
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
public class clienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    ////-------------- Listar Clientes ---------------------////
    @RequestMapping(value = "/listClientes", method = RequestMethod.GET)
    public List<Cliente> findAll(){
        List<Cliente> all = clienteService.findAll();
        return all;
    }

    ////-------------- Guardar Clientes ---------------------////

    @PostMapping(value = "saveCliente")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Cliente cliente){

        Cliente cliente1= null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            cliente1=clienteService.save(cliente);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido creado con éxito!");
        response.put("Cliente",cliente1);

        return new ResponseEntity<Map<String,Object>>(response, OK);


    }


    ////-------------- Eliminar Clientes ---------------------////
    @RequestMapping(value = "/DeleteCliente/{id}",  method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String,Object> response =new HashMap<String, Object>();
        try {
            clienteService.deletebyID(id);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al eliminar el cliente de la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente fue eliminado con éxito!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

}