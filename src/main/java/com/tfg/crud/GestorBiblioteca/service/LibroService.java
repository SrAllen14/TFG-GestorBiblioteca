/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Libro;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Usuario
 */
public interface LibroService extends UserDetailsService{
    
    public Libro registarLibro(Libro libro);
    public List<Libro> listarLibros();
    public Libro buscarLibroPorTitulo(String titulo);
    public Libro buscarLibroPorId(Long id);
    public Libro editarLibro(Libro libro);
    public boolean inhabilitarLibro(Long id);
    public boolean rehabilitarLibro(Long id);
}
