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
 *
 * @author Usuarioa
 */
@Controller
@RequestMapping("/ejemplar")
public class ejemplarController {
    
    @Autowired
    private LibroService libroService;
    
    @Autowired
    private EjemplarService ejemplarService;
        
    
    @PostMapping("/{idLibro}/registro")
    public String registrarEjemplar(@PathVariable Long idLibro){
    
        ejemplarService.registrarEjemplar(idLibro);
        
        return "redirect:/libro/consultar/"+idLibro;
    }
}
