package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import com.tfg.crud.GestorBiblioteca.repository.UsuarioRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de la autenticación de usuarios en el sistema.
 * Implementa UserDetailsService de Spring Security para cargar los
 * datos del usuario durante el proceso de login.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Carga un usuario del sistema a partir de su nombre de usuario.
     * 
     * @param username nombre de usuario utilizado en el login
     * @return detalles del usuario para Spring Security
     * @throws UsernameNotFoundException si el usuario no existe
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        return new User(usuario.getUsername(), usuario.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().name())));
    }
}
