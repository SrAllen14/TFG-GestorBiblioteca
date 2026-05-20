/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Usuario
 */

@Controller
@RequestMapping("/prestamo")
public class mtoPrestamo {
    
    @Autowired
    private PrestamoService prestamoService;
    
    @GetMapping
    public String mostrarPrestamos(Model modelo){
        modelo.addAttribute("prestamos", prestamoService.listarPrestamos());
        
        return "mtoPrestamos";
    }
}
