/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import com.tfg.crud.GestorBiblioteca.entity.Libro;
import com.tfg.crud.GestorBiblioteca.repository.EjemplarRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class EjemplarServiceImp implements EjemplarService{
    
    @Autowired
    private EjemplarRepository ejemplarRepository;
    
    @Autowired
    private LibroService libroService;

    @Override
    public Ejemplar registrarEjemplar(Long idLibro) {
        
        Libro libro = libroService.buscarLibroPorId(idLibro);
        
        Long numeroEjemplar = ejemplarRepository.countByLibroIdLibro(idLibro) + 1;
        
        String codigo = libro.getIsbn() + "-" + numeroEjemplar;
        
        Ejemplar ejemplar = new Ejemplar();
        
        ejemplar.setLibro(libro);
        ejemplar.setActivo(true);        
        ejemplar.setCodigo(codigo);
        
        ejemplarRepository.save(ejemplar);
        
        return ejemplar;
    }

    @Override
    public List<Ejemplar> listarEjemplares() {

        return ejemplarRepository.findAll();
    }

    @Override
    public void darDeBajaEjemplar(Long id) {
        Ejemplar ejemplar = buscarEjemplarPorId(id);
        
        ejemplar.setActivo(!ejemplar.isActivo());
        
        ejemplarRepository.save(ejemplar);
    }

    @Override
    public Ejemplar buscarEjemplarPorId(Long id) {

        return ejemplarRepository.findById(id).orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));
    }

    @Override
    public List<Ejemplar> listarEjemplaresPorLibro(Long id) {

        return ejemplarRepository.findByLibroIdLibro(id);
    }

    @Override
    public List<Ejemplar> listarEjemplaresDisponibles() {
        
        List<Ejemplar> ejemplares = ejemplarRepository.buscarEjemplaresDisponibles();
        
        return ejemplares.stream().filter(e -> e.getPrestamos().stream().noneMatch((p -> p.getFechaDevolucion() == null))).toList();
    }
}
