/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.entity.Libro;
import com.tfg.crud.GestorBiblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Usuario
 */

@Controller
@RequestMapping("/libro")
public class mtoLibrosController {
    
    @Autowired
    private LibroService libroService;
    
    @GetMapping
    public String mostrarLibros(Model modelo){
    
        modelo.addAttribute("libros",libroService.listarLibros());
        return "mtoLibros";
    }
    
    @GetMapping("/crear")
    public String mostrarRegistroLibro(Model modelo){
    
        modelo.addAttribute("libro", new Libro());
        
        return "/registroLibro";
    }
    
    @PostMapping("/crear")
    public String registrarLibro(@ModelAttribute Libro libro, RedirectAttributes redirectAttributes){
    
        try{
            libroService.registarLibro(libro);
            redirectAttributes.addFlashAttribute("success", "Usuario registrado");
        } catch(RuntimeException re){
            redirectAttributes.addFlashAttribute("error", re.getMessage());
            return "redirect:/libro/crear";
        }
        
        return "redirect:/libro";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarEditarLibro(Model modelo, @PathVariable Long id){
    
        Libro libro = libroService.buscarLibroPorId(id);
        modelo.addAttribute("libro", libro);
        
        return "edicionLibro";
    }

    @PostMapping("/editar/{id}")
    public String editarLibro(@PathVariable Long id, @ModelAttribute Libro libro){
        
        libroService.editarLibro(id, libro);
        return "redirect:/libro";
    }
    
    @PostMapping("/estado/{id}")
    public String cambiarEstadoUsuario(@PathVariable Long id){
        
        libroService.modificarEstadoLibro(id);
        return "redirect:/libro";
    }
}

