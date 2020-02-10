package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Usuario;

import java.util.List;

public interface IUsuarioService{

    public Usuario findByUsername(String username);
    public Usuario save(Usuario us);
    public Usuario findById(Long id);
    public void saveUsuario_Roles(Long id_User, Long id_Rol);
}

