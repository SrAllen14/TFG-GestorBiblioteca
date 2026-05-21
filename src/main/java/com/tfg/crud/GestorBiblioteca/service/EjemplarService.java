/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface EjemplarService{
    
    public Ejemplar registrarEjemplar(Long idLibro);
    public Ejemplar buscarEjemplarPorId(Long id);
    public List<Ejemplar> listarEjemplaresPorLibro(Long idLibro);
    public List<Ejemplar> listarEjemplares();
    public List<Ejemplar> listarEjemplaresDisponibles(Long idLibro);
    public void darDeBajaEjemplar(Long id);
}
