/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.dto.PrestamoDTO;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import com.tfg.crud.GestorBiblioteca.service.EjemplarService;
import com.tfg.crud.GestorBiblioteca.service.LibroService;
import com.tfg.crud.GestorBiblioteca.service.PrestamoService;
import com.tfg.crud.GestorBiblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Usuario
 */

@Controller
@RequestMapping("/prestamo")
public class mtoPrestamoController {
    
    @Autowired
    private PrestamoService prestamoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private LibroService libroService;
    
    @Autowired
    private EjemplarService ejemplarService;
    
    @GetMapping
    public String mostrarPrestamos(Model modelo){
        modelo.addAttribute("prestamos", prestamoService.listarPrestamos());
        
        return "mtoPrestamos";
    }
    
    @GetMapping("/registro")
    public String mostrarRegistroPrestamo(Model modelo, @RequestParam(required = false) String nombre, @RequestParam(required = false) String isbn){
        
        modelo.addAttribute("prestamoDTO", new PrestamoDTO());
        modelo.addAttribute("usuariosDisponibles", usuarioService.buscarUsuariosDisponibles(nombre));
        modelo.addAttribute("librosDisponibles", libroService.listarLibrosDisponibles(isbn));
        
        modelo.addAttribute("nombreBuscado", nombre);
        modelo.addAttribute("isbnBuscado", isbn);
        
        return "registroPrestamo";
    }
            
    @PostMapping("/registro")      
    public String registrarPrestamo(@ModelAttribute PrestamoDTO prestamoDTO, RedirectAttributes redirectAttributes){
    
        try{
            prestamoService.registrarPretamo(prestamoDTO);
        } catch(RuntimeException re){
            redirectAttributes.addFlashAttribute("error", re.getMessage());
            return "redirect:/prestamo/registro";
        }
        return "redirect:/prestamo";
    }
    /*
    @GetMapping("/editar/{idPrestamo}")
    public String mostrarEditarPrestamo(Model modelo, @PathVariable Long idPrestamo){
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(idPrestamo);
        
        PrestamoDTO prestamoDTO = new PrestamoDTO();
        
        return "edicionPrestamo";
    }
*/
}
