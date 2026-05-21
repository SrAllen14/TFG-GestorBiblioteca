/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.repository;

import com.tfg.crud.GestorBiblioteca.entity.Rol;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findByActivoTrueAndTipoInAndNombreContainingIgnoreCase(List<Rol> roles, String nombre);
}
