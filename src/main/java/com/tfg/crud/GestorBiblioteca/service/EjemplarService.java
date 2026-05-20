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
    
    public Ejemplar registrarEjemplar(Ejemplar ejemplar);
    public Ejemplar buscarEjemplarPorId(Long id);
    public List<Ejemplar> listarEjemplaresPorLibro(Long id);
    public List<Ejemplar> listarEjemplares();
    public void darDeBajaEjemplar(Long id);
}
