/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import com.tfg.crud.GestorBiblioteca.entity.Libro;
import com.tfg.crud.GestorBiblioteca.service.EjemplarService;
import com.tfg.crud.GestorBiblioteca.service.LibroService;
import jakarta.validation.Valid;
import java.util.List;
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

    @Autowired
    private EjemplarService ejemplarService;

    @GetMapping
    public String mostrarLibros(Model modelo) {

        modelo.addAttribute("libros", libroService.listarLibros());
        modelo.addAttribute("librosDisponibles", libroService.listarLibrosDisponibles(null));
        modelo.addAttribute("ejemplaresDisponibles", ejemplarService.listarEjemplaresDisponibles(Long.valueOf("4")));
        
        return "mtoLibros";
    }

    @GetMapping("/consultar/{idLibro}")
    public String consultarLibro(Model modelo, @PathVariable Long idLibro) {

        Libro libro = libroService.buscarLibroPorId(idLibro);
        List<Ejemplar> ejemplares = ejemplarService.listarEjemplaresPorLibro(idLibro);

        modelo.addAttribute("libro", libro);
        modelo.addAttribute("ejemplares", ejemplares);

        return "detalleLibro";
    }

    @GetMapping("/crear")
    public String mostrarRegistroLibro(Model modelo) {

        modelo.addAttribute("libro", new Libro());

        return "/registroLibro";
    }

    @PostMapping("/crear")
    public String registrarLibro(@Valid @ModelAttribute Libro libro, RedirectAttributes redirectAttributes) {

        try {
            libroService.registarLibro(libro);
            redirectAttributes.addFlashAttribute("success", "Usuario registrado");
        } catch (RuntimeException re) {
            redirectAttributes.addFlashAttribute("error", re.getMessage());
            return "redirect:/libro/crear";
        }

        return "redirect:/libro";
    }

    @GetMapping("/editar/{idLibro}")
    public String mostrarEditarLibro(Model modelo, @PathVariable Long idLibro) {

        Libro libro = libroService.buscarLibroPorId(idLibro);
        modelo.addAttribute("libro", libro);

        return "edicionLibro";
    }

    @PostMapping("/editar/{idLibro}")
    public String editarLibro(@Valid @PathVariable Long idLibro, @ModelAttribute Libro libro) {

        libroService.editarLibro(idLibro, libro);
        return "redirect:/libro";
    }

    @PostMapping("/estado/{idLibro}")
    public String cambiarEstadoUsuario(@PathVariable Long idLibro) {

        libroService.modificarEstadoLibro(idLibro);
        return "redirect:/libro";
    }
}
