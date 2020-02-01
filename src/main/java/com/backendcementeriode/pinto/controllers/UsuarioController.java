package com.backendcementeriode.pinto.controllers;

import com.backendcementeriode.pinto.models.Entity.Usuario;
import com.backendcementeriode.pinto.models.Entity.cambioPass;
import com.backendcementeriode.pinto.models.Service.classImpl.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/cambioPass/{id}")
    public ResponseEntity<?> cambiarPass(@RequestBody cambioPass cambioPass, @PathVariable Long id) {
        Usuario user2 = usuarioService.findById(id);
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            if (passwordEncoder.matches(cambioPass.getAntigua(), user2.getPassword()) ){
                user2.setPassword(passwordEncoder.encode(cambioPass.getNueva()));
                updateuser(user2);
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
    public void updateuser(Usuario user){
        usuarioService.save(user);
    }



}


