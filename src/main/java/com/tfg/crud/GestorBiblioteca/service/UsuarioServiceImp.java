/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.UsuarioDTO;
import com.tfg.crud.GestorBiblioteca.entity.Rol;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import com.tfg.crud.GestorBiblioteca.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */

@Service
public class UsuarioServiceImp implements UsuarioService{
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(UsuarioDTO usuarioDTO) {
        
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido1(usuarioDTO.getApellido1());
        usuario.setApellido2(usuarioDTO.getApellido2());
        usuario.setRol(usuarioDTO.getRol());
        usuario.setActivo(true);
        
        // Debemos comprobar que tipo de usuario es
        // En caso de ser "BIBLIOTECARIO" o "ADMINISTADOR" deberá tener username y password
        if(usuarioDTO.getRol() == Rol.ROLE_BIBLIOTECARIO || usuarioDTO.getRol() == Rol.ROLE_ADMINISTRADOR){
            
            // Comprobamos si el campo de "username" está vacio
            if(usuarioDTO.getUsername() == null || usuarioDTO.getUsername().isBlank()){
                // Si está vacio lanzamos una excepción
                throw new RuntimeException("El identificativo de usuario es obligatorio");
            }
            
            // Comprobamos si el campo de "password" está vacio
            if(usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isBlank()){
                // Si está vacio lanzamos una excepción
                throw new RuntimeException("La contraseña es obligatoria");
            }
            
            // En caso de no haber error, introducimos los datos en el usuario.
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
            
        } else{
            
            // En caso de no ser bibliotecario u administrador, introducimos valor nulo en el usuario.
            usuario.setUsername(null);
            usuario.setPassword(null);
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rolUsuario = auth.getAuthorities().iterator().next().getAuthority();
        
        if(rolUsuario.equals("ROLE_BIBLIOTECARIO")){
            
            if(usuarioDTO.getRol() == Rol.ROLE_ADMINISTRADOR || usuarioDTO.getRol() == Rol.ROLE_BIBLIOTECARIO){
                throw new RuntimeException("Un bibliotecario no puede crear administradores ni bibliotecarios");
            }
        }
        
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public void modificarEstadoUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        
        usuario.setActivo(!usuario.isActivo());
        
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario editarUsuario(Long id, Usuario usuarioEditado) {
        
        Usuario usuario = buscarUsuarioPorId(id);
        
        usuario.setNombre(usuarioEditado.getNombre());
        usuario.setApellido1(usuarioEditado.getApellido1());
        usuario.setApellido2(usuarioEditado.getApellido2());
        usuario.setRol(usuarioEditado.getRol());
        
        if(usuarioEditado.getRol() == Rol.ROLE_ADMINISTRADOR || usuarioEditado.getRol() == Rol.ROLE_BIBLIOTECARIO){
        
            usuario.setUsername(usuarioEditado.getUsername());
            
            if(usuarioEditado.getPassword() != null && usuarioEditado.getPassword().isBlank()){
                usuario.setPassword(passwordEncoder.encode(usuarioEditado.getPassword()));
            }
        } else{
            usuario.setUsername(null);
            usuario.setPassword(null);
        }
        
        return usuarioRepository.save(usuario);

    }
}
