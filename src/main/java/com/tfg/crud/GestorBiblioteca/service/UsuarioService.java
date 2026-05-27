/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.UsuarioDTO;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Usuario
 */
public interface UsuarioService{
    
    public Usuario registrarUsuario(UsuarioDTO usuarioDTO);
    public List<Usuario> listarUsuarios();
    public Usuario buscarUsuarioPorId(Long id);
    public Usuario buscarUsuarioPorUsername(String id);
    public List<Usuario> buscarUsuariosDisponibles(String filtroNombre);
    public Page<Usuario> buscarUsuarios(String busqueda, Boolean activo, Pageable pageable);
    public Usuario editarUsuario(Long id, UsuarioDTO usuarioEditadoDTO);
    public void modificarEstadoUsuario(Long id);
}
