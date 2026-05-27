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
 *
 * @author Usuario
 */

@RestController
@RequestMapping("/api/inicio")
public class InicioRestController {
    
    @Autowired
    private InicioService inicioService;
    
    @GetMapping
    public InicioDTO getInicio(){
        return inicioService.obtenerDatos();
    }
}
