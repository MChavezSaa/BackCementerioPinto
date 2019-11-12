package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Funcionario;
import com.backendcementeriode.pinto.models.Service.classImpl.FuncionarioServiceImpl;
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
public class funcionarioController {

    @Autowired
    private FuncionarioServiceImpl funcionarioService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listFuncionarios", method = RequestMethod.GET )
    public List<Funcionario> findAll() {
        List<Funcionario> all = funcionarioService.findAll();
        return all;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value= "/saveFuncionario")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Funcionario funcionario){
        funcionario.setEstado_funcionario(true);
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

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/DeleteFuncionario/{id}",  method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Funcionario funcionarioBuscado = funcionarioService.findById(id);
        Funcionario funcionario2 = null;

        Map<String,Object> response =new HashMap<String, Object>();
        try {
            funcionarioBuscado.setEstado_funcionario(false);
            funcionario2= funcionarioService.save(funcionarioBuscado);
            //funcionarioService.deletebyID(id);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al eliminar el funcionario de la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El funcionario fue eliminado con éxito!");
        response.put("Funcionario: ", funcionario2);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }


    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/darAlta/{id}")
    public ResponseEntity<?> darAlta(@RequestBody Funcionario funcionario, @PathVariable Long id) {
        Funcionario funcionarioActual=funcionarioService.findById(id);
        funcionarioActual.setEstado_funcionario(true);
        Funcionario funcionarioUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(funcionarioActual==null) {
            response.put("mensaje","No se pudo dar de alta, el funcionario con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            funcionarioUpdated=funcionarioService.save(funcionarioActual);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al dar de alta el funcionario en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El funcionario ha sido dado de alta con éxito!");
        response.put("funcionario",funcionarioUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }




    @Secured("ROLE_ADMIN")
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
           // funcionarioActual.setId_funcionario(funcionario.getId_funcionario());
            funcionarioActual.setRut_Funcionario(funcionario.getRut_Funcionario());
            funcionarioActual.setNombres_Funcionario(funcionario.getNombres_Funcionario());
            funcionarioActual.setApellidoP_Funcionario(funcionario.getApellidoP_Funcionario());
            funcionarioActual.setApellidoM_Funcionario(funcionario.getApellidoM_Funcionario());
            funcionarioActual.setCargo_Funcionario(funcionario.getCargo_Funcionario());
            //funcionarioActual.setEstado_funcionario(funcionario.isEstado_funcionario());
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

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findFuncionario/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Funcionario funcionario=null;
        Map<String,Object> response =new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            funcionario=funcionarioService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if(funcionario==null) {
            response.put("mensaje","El funcionario con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(funcionario,HttpStatus.OK);
    }


}
