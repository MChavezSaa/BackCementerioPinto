package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Funcionario;
import com.backendcementeriode.pinto.models.Service.FuncionarioServiceImpl;
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
public class funcionarioController {

    @Autowired
    private FuncionarioServiceImpl funcionarioService;

    @RequestMapping(value = "/listFuncionarios", method = RequestMethod.GET )
    public List<Funcionario> findAll() {
        List<Funcionario> all = funcionarioService.findAll();
        return all;
    }
    @PostMapping(value= "/saveFuncionario")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Funcionario funcionario){

        Funcionario funcionario1=null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            funcionario1=funcionarioService.save(funcionario);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El funcionario ha sido creado con éxito!");
        response.put("Funcionario",funcionario1);

        return new ResponseEntity<Map<String,Object>>(response, OK);
    }


    @RequestMapping(value = "/DeleteFuncionario/{id}",  method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String,Object> response =new HashMap<String, Object>();
        try {
            funcionarioService.deletebyID(id);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al eliminar el funcionario de la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El funcionario fue eliminado con éxito!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }
    @PutMapping(value ="/updateFuncionario/{id}")
    public ResponseEntity<?> update(@RequestBody Funcionario funcionario, @PathVariable Long id) {
        Funcionario funcionarioActual=funcionarioService.findById(id);
        Funcionario funcionarioUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(funcionarioActual==null) {
            response.put("mensaje","No se pudo editar, el funcionario con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            funcionarioActual.setId(funcionario.getId());
            funcionarioActual.setNombre(funcionario.getNombre());
            funcionarioActual.setApellido(funcionario.getApellido());
            funcionarioActual.setCargo(funcionario.getCargo());


            funcionarioUpdated=funcionarioService.save(funcionarioActual);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el funcionario en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El funcionario ha sido actualizado con éxito!");
        response.put("funcionario",funcionarioUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }



}
