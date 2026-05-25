/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.repository;

import com.tfg.crud.GestorBiblioteca.entity.Libro;
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
public interface LibroRepository extends JpaRepository<Libro, Long>{
    
    @Query("""
        SELECT l FROM Libro l
        WHERE (:busqueda IS NULL OR :busqueda = ''
           OR LOWER(l.titulo) LIKE LOWER(CONCAT('%', :busqueda, '%'))
           OR l.isbn LIKE CONCAT('%', :busqueda, '%'))
           AND (:activo IS NULL OR l.activo = :activo)
    """)
    Page<Libro> buscarLibros(@Param("busqueda") String busqueda, @Param("activo") Boolean activo, Pageable pageable);
    
    @Query("""
        SELECT l FROM Libro l
        WHERE (:busqueda IS NULL OR :busqueda = '')
           OR LOWER(l.titulo) LIKE LOWER(CONCAT('%', :busqueda, '%'))
           OR LOWER(l.isbn) LIKE LOWER(CONCAT('%', :busqueda, '%'))
    """)
    Page<Libro> buscarTodosLibros(@Param("busqueda") String busqueda, Pageable pageable);
    
    List<Libro> findByIsbnContainingAndActivoTrue(String isbn);
}
