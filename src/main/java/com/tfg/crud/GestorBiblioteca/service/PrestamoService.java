/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.PrestamoDTO;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Usuario
 */
public interface PrestamoService extends UserDetailsService{
    
    public Prestamo registrarPretamo(PrestamoDTO prestamoDTO);
    public List<Prestamo> listarPrestamos();
    public Prestamo buscarPrestamoPorId(Long id);
    public Prestamo editarPrestamo(PrestamoDTO prestamoDTO);
    public boolean finalizarPrestamo(LocalDate fechaDevolucion, Long id);
}
