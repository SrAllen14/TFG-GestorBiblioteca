/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador encargado de mostrar la página de inicio de sesión
 * de la aplicación
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */

@Controller
@RequestMapping("/login")
public class loginController {
    
    /**
     * Muestra el formulario de inicio de sesión.
     * 
     * @return Vista de login
     */
    @GetMapping
    public String mostrarLogin(){

        return "login";
    }
}
