/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.repository;

import com.tfg.crud.GestorBiblioteca.entity.Rol;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    @Query("""
        SELECT u FROM Usuario u
        WHERE (:busqueda IS NULL OR :busqueda = ''
           OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%'))
           OR LOWER(u.dni) LIKE LOWER(CONCAT('%', :busqueda, '%')))
           AND (:activo IS NULL OR u.activo = :activo)
    """)
    Page<Usuario> buscarUsuarios(@Param("busqueda") String busqueda, @Param("activo") Boolean activo, Pageable pageable);
    
    @Query("""
        SELECT u FROM Usuario u
            WHERE (:busqueda IS NULL OR :busqueda = ''
            OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%'))
            OR LOWER(u.dni) LIKE LOWER(CONCAT('%', :busqueda, '%')))
    """)
    Page<Usuario> buscarTodosUsuarios(@Param("busqueda") String busqueda, Pageable pageable);
    
    List<Usuario> findByActivoTrueAndTipoInAndNombreContainingIgnoreCase(List<Rol> roles, String nombre);
    
    Long countByActivoTrueAndTipoIn(List<Rol> roles);
    
    Optional<Usuario> findByUsername(String username);
    Usuario findByDni(String dni);
    Boolean existsByDni(String dni);
    Boolean existsByUsername(String username);
}
