/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de gestionar la página principal de la aplicación.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */

@Controller
public class inicioController {
    
    /**
     * Muestra la página de inicio de la aplicación.
     * 
     * @return nombre de la vista principal
     */
    @GetMapping("/")
    public String inicio(){
    
        return "inicio";
    }
}
