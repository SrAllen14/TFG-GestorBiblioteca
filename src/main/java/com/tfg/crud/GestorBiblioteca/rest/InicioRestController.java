/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.rest;

import com.tfg.crud.GestorBiblioteca.dto.InicioDTO;
import com.tfg.crud.GestorBiblioteca.service.InicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST encargado de proporcionar los datos
 * mostrador en la página principal de la aplicación.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */

@RestController
@RequestMapping("/api/inicio")
public class InicioRestController {
    
    @Autowired
    private InicioService inicioService;
    
    /**
     * Obtiene los datos estadisticos necesarios para la
     * página de inicio de la aplicación.
     * 
     * @return Objeto con la información de inicio
     */
    @GetMapping
    public InicioDTO getInicio(){
        return inicioService.obtenerDatos();
    }
}
