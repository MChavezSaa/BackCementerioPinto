package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.*;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleServiceImpl roleService;


    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/cambioPass")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Usuario user) {

        Usuario user1 = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            Usuario user2 = user;

            user1 = usuarioService.save(user2);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido actualizado con éxito!");
        response.put("Usuario", user1);

        return new ResponseEntity<Map<String, Object>>(response, OK);
    }



}