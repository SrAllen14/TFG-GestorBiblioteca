/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Libro;
import com.tfg.crud.GestorBiblioteca.repository.LibroRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if(libroRepository.existsByIsbn(libro.getIsbn())){
            throw new IllegalArgumentException("El ISBN existe en el sistema");
        }
        libro.setActivo(true);
        
        return libroRepository.save(libro);
    }

    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Libro buscarLibroPorId(Long id) {
        return libroRepository.findById(id).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    @Override
    public Libro editarLibro(Long id, Libro libroEditado) {
        
        Libro libro = buscarLibroPorId(id);
        
        if(libroRepository.existsByIsbn(libroEditado.getIsbn())){
            throw new IllegalArgumentException("El ISBN existe en el sistema");
        }
        
        libro.setTitulo(libroEditado.getTitulo());
        libro.setAutor(libroEditado.getAutor());
        libro.setGenero(libroEditado.getGenero());
        libro.setEditorial(libroEditado.getEditorial());
        libro.setIsbn(libroEditado.getIsbn());
        
        
        libroRepository.save(libro);
        
        return libro;
    }

    @Override
    public void modificarEstadoLibro(Long id) {

        Libro libro = buscarLibroPorId(id);
        
        libro.setActivo(!libro.isActivo());
        
        libroRepository.save(libro);
    }

    @Override
    public List<Libro> listarLibrosDisponibles(String isbn) {
        
        if(isbn == null || isbn.isBlank()){
            isbn = "";
        }
        
        List <Libro> libros = libroRepository.findByIsbnContainingAndActivoTrue(isbn);
        
        return libros.stream().filter(l ->!l.getEjemplares().isEmpty()).toList();
    }

    @Override
    public Page<Libro> buscarLibros(String busqueda, Boolean activo, Pageable pageable) {
        if(activo == null){
            return libroRepository.buscarTodosLibros(busqueda, pageable);
        }
        return libroRepository.buscarLibros(busqueda, activo, pageable);
    }
}
