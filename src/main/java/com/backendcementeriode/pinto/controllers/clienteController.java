package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Cliente;
import com.backendcementeriode.pinto.models.Entity.Funcionario;
import com.backendcementeriode.pinto.models.Entity.Role;
import com.backendcementeriode.pinto.models.Entity.Usuario;
import com.backendcementeriode.pinto.models.Service.classImpl.ClienteServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.RoleServiceImpl;
import com.backendcementeriode.pinto.models.Service.classImpl.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "*")
@RestController
public class clienteController {

    @Autowired
    private ClienteServiceImpl clienteService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    List<Role> rolesList ;
    ////-------------- Listar Clientes ---------------------////
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listClientes", method = RequestMethod.GET)
    public List<Cliente> findAll(){
        List<Cliente> all = clienteService.findAll();
        return all;
    }

    ////-------------- Guardar Clientes ---------------------////
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveCliente")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
        rolesList = roleService.findAll();
        cliente.setEstadoCliente(true);
        Cliente cliente1= null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            cliente1=clienteService.save(cliente);
            Usuario us = new Usuario();
            String pass1 ="12345";
            String psEncoder2 = passwordEncoder.encode(pass1);
            us.setPassword(psEncoder2);
            us.setUsername(cliente.getRut_Cliente());
            us.setEnable(true);
            System.out.println(us.toString());
            System.out.println(rolesList.get(1).toString());
            usuarioService.save(us);
            usuarioService.saveUsuario_Roles(us.getId_Usuario(),rolesList.get(1).getId_Role() );


        }catch(DataAccessException e) {
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido creado con éxito!");
        response.put("Cliente",cliente1);

        return new ResponseEntity<Map<String,Object>>(response, OK);


    }

    ////---------------Actualizar Clientes -------------------////
    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/updateCliente/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente clienteActual=clienteService.findById(id).get();
        Cliente clienteUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(clienteActual==null) {
            response.put("mensaje","No se pudo editar, el cliente con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            // clienteActual.setId_Cliente(cliente.getId_Cliente());
            clienteActual.setRut_Cliente(cliente.getRut_Cliente());
            clienteActual.setNombres_Cliente(cliente.getNombres_Cliente());
            clienteActual.setApellidoP_Cliente(cliente.getApellidoP_Cliente());
            clienteActual.setApellidoM_Cliente(cliente.getApellidoM_Cliente());
            clienteActual.setGenero_Cliente(cliente.getGenero_Cliente());
            clienteActual.setTelefono_Cliente(cliente.getTelefono_Cliente());
            clienteActual.setDireccion_Cliente(cliente.getDireccion_Cliente());

            /////Familiar
            clienteActual.setRut_Familiar(cliente.getRut_Familiar());
            clienteActual.setNombres_Familiar(cliente.getNombres_Familiar());
            clienteActual.setApellidoP_Familiar(cliente.getApellidoP_Familiar());
            clienteActual.setApellidoM_Familiar(cliente.getApellidoM_Familiar());
            clienteActual.setTelefono_Familiar(cliente.getTelefono_Familiar());
            //clienteActual.setEstadoCliente(cliente.isEstadoCliente());
            clienteUpdated=clienteService.save(clienteActual);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el cliente en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido actualizado con éxito!");
        response.put("cliente",clienteUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }


    ////-------------- Eliminar Clientes ---------------------////
    @Secured("ROLE_ADMIN")
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

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findCliente/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Cliente cliente=null;
        Map<String,Object> response =new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            cliente=clienteService.findById(id).get();
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if(cliente==null) {
            response.put("mensaje","El cliente con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(cliente,HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/darAltaCliente/{id}")
    public ResponseEntity<?> darAlta(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente clienteActual=clienteService.findById(id).get();
        clienteActual.setEstadoCliente(true);
        Cliente clienteUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(clienteActual==null) {
            response.put("mensaje","No se pudo dar de alta, el cliente con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            clienteUpdated=clienteService.save(clienteActual);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al dar de alta el cliente en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido dado de alta con éxito!");
        response.put("cliente",clienteUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }


}