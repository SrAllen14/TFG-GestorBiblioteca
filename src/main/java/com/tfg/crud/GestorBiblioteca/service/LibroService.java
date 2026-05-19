/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Libro;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface LibroService{
    
    public Libro registarLibro(Libro libro);
    public List<Libro> listarLibros();
    public Libro buscarLibroPorTitulo(String titulo);
    public Libro buscarLibroPorId(Long id);
    public Libro editarLibro(Long id, Libro libro);
    public void modificarEstadoLibro(Long id);
}
