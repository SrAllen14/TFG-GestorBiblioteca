/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.InicioDTO;
import com.tfg.crud.GestorBiblioteca.entity.Rol;
import com.tfg.crud.GestorBiblioteca.repository.LibroRepository;
import com.tfg.crud.GestorBiblioteca.repository.PrestamoRepository;
import com.tfg.crud.GestorBiblioteca.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class InicioService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    public InicioDTO obtenerDatos() {

        InicioDTO dto = new InicioDTO();
        List<Rol> rolesPermitidos = List.of(Rol.ROLE_ALUMNO, Rol.ROLE_PROFESOR);
        
        dto.setPrestamosActivos(
            prestamoRepository.countByFechaDevolucionIsNull()
        );

        dto.setUsuariosActivos(
            usuarioRepository.countByActivoTrueAndTipoIn(rolesPermitidos)
        );

        dto.setLibrosActivos(
            libroRepository.countByActivoTrue()
        );

        return dto;
    }
}
