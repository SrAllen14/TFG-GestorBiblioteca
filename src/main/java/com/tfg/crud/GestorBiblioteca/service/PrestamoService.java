/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.PrestamoDTO;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */

@Service
public interface PrestamoService{
    
    public Prestamo registrarPretamo(PrestamoDTO prestamoDTO);
    public List<Prestamo> listarPrestamos();
    public Prestamo buscarPrestamoPorId(Long idPrestamo);
    public Prestamo editarPrestamo(PrestamoDTO prestamoDTO);
    public void finalizarPrestamo(LocalDate fechaDevolucion, Long idPrestamo);
    public void reabrirPrestamo(Long idPrestamo);
}
