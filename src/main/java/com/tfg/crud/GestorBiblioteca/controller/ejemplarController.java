/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.service.EjemplarService;
import com.tfg.crud.GestorBiblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador encargado de gestionar las operaciones relacionadas
 * con los ejemplares de los libros.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
@Controller
@RequestMapping("/ejemplar")
public class ejemplarController {
    
    @Autowired
    private LibroService libroService;
    
    @Autowired
    private EjemplarService ejemplarService;
        
    /**
     * Registra un nuevo ejemplar asociado a un libro.
     * 
     * @param idLibro Identificador del libro al que se añadirá el ejemplar
     * @return Redirección a la ruta /libro/consultar/{idLibro} o la vista de detalles de un libro
     */
    @PostMapping("/{idLibro}/registro")
    public String registrarEjemplar(@PathVariable Long idLibro){
    
        ejemplarService.registrarEjemplar(idLibro);
        
        return "redirect:/libro/consultar/"+idLibro;
    }
}
