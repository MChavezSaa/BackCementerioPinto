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


    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/cambioPass")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Usuario user) {
        Map<String, Object> response = new HashMap<String, Object>();
        //traemos usuario antiguo para cambiar password
        Usuario userBDD = usuarioService.findById(user.getId_Usuario());
        try {
            /*verificar igualdad de password antigua(guardada en BDD) y la antigua pasada por el front*/
            if (passwordEncoder.matches(user.getPassword(), userBDD.getPassword())) {
                userBDD.setPassword(passwordEncoder.encode(user.getPassword()));
                usuarioService.save(userBDD);
            }
        } catch (DataAccessException e) {

            response.put("mensaje", "Error al actualizar contraseña");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "El funcionario ha sido actualizado con éxito!");
        response.put("funcionario", response);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }


}