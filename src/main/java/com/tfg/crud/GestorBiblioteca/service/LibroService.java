/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Libro;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Usuario
 */
public interface LibroService{
    
    public Libro registarLibro(Libro libro);
    public List<Libro> listarLibros();
    public Libro buscarLibroPorId(Long id);
    public List<Libro> listarLibrosDisponibles(String isbn);
    public Page<Libro> buscarLibros(String busqueda, Boolean activo, Pageable pageable);
    public Libro editarLibro(Long id, Libro libro);
    public void modificarEstadoLibro(Long id);
}
