/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.repository;

import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long>{
   
    List<Ejemplar> findByLibroIdLibro(Long idLibro);
    Long countByLibroIdLibro(Long idLibro);
    List<Ejemplar> findByLibroIdLibroAndActivoTrue(Long idLibro);
    
}
