package com.backendcementeriode.pinto.models.Service.SeviceInterface;

import com.backendcementeriode.pinto.models.Entity.Usuario;

public interface IUsuarioService{

    public Usuario findByUsername(String username);
}

