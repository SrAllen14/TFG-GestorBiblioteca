/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.UsuarioDTO;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface UsuarioService{
    
    public Usuario registrarUsuario(UsuarioDTO usuarioDTO);
    public List<Usuario> listarUsuarios();
    public Usuario buscarUsuarioPorNombre(String nombre);
    public Usuario buscarUsuarioPorId(Long id);
    public Usuario editarUsuario(Long id, Usuario usuarioEditado);
    public void modificarEstadoUsuario(Long id);
}
