/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.repository;

import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import java.time.LocalDate;
import java.util.List;
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
public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{
    
    List<Prestamo> findByEjemplarCodigoContainingIgnoreCase(String codigoEjemplar);
    List<Prestamo> findByUsuarioIdUsuario(Long idUsuario);
    
    @Query("""
           SELECT p FROM Prestamo p WHERE (:busqueda IS NULL 
           OR :busqueda = '' OR p.ejemplar.codigo LIKE CONCAT('%', :busqueda, '%')
           OR LOWER(p.usuario.nombre) LIKE LOWER(CONCAT('%',:busqueda, '%')))
           """)
    Page<Prestamo> buscarTodosPrestamos(@Param("busqueda") String busqueda, Pageable pageable);
    
    @Query("""
            SELECT p FROM Prestamo p WHERE (
                :busqueda IS NULL OR :busqueda = ''
                OR LOWER(p.usuario.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%'))
                OR LOWER(p.ejemplar.codigo) LIKE LOWER(CONCAT('%', :busqueda, '%')))
            AND (:activo IS NULL
                OR (:activo = true AND p.fechaDevolucion IS NULL)
                OR (:activo = false AND p.fechaDevolucion IS NOT NULL))
        """)
    Page<Prestamo> buscarPrestamos(
            @Param("busqueda") String busqueda,
            @Param("activo") Boolean activo,
            Pageable pageable);
}
