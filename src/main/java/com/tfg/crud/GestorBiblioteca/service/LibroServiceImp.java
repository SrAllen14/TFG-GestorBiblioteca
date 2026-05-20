/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Libro;
import com.tfg.crud.GestorBiblioteca.repository.LibroRepository;
import com.tfg.crud.GestorBiblioteca.validation.IsbnValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class LibroServiceImp implements LibroService{

    @Autowired
    private LibroRepository libroRepository;
    
    @Override
    public Libro registarLibro(Libro libro) {
        
        libro.setActivo(true);
        
        if (!IsbnValidator.isValid(libro.getIsbn())){
            throw new IllegalArgumentException("ISBN inválido");
        }
        
        return libroRepository.save(libro);
    }

    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Libro buscarLibroPorTitulo(String titulo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Libro buscarLibroPorId(Long id) {
        return libroRepository.findById(id).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    @Override
    public Libro editarLibro(Long id, Libro libroEditado) {
        
        Libro libro = buscarLibroPorId(id);
        
        libro.setTitulo(libroEditado.getTitulo());
        libro.setAutor(libroEditado.getAutor());
        libro.setGenero(libroEditado.getGenero());
        
        if (!IsbnValidator.isValid(libroEditado.getIsbn())){
            throw new IllegalArgumentException("ISBN inválido");
        } else{
            libro.setIsbn(libroEditado.getIsbn());
        }
        
        libroRepository.save(libro);
        
        return libro;
    }

    @Override
    public void modificarEstadoLibro(Long id) {

        Libro libro = buscarLibroPorId(id);
        
        libro.setActivo(!libro.isActivo());
        
        libroRepository.save(libro);
    }
}
