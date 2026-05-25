/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.PrestamoDTO;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */

@Service
public interface PrestamoService{
    
    public Prestamo registrarPrestamo(PrestamoDTO prestamoDTO);
    public List<Prestamo> listarPrestamosPorCodigo(String codigo);
    public Prestamo buscarPrestamoPorId(Long idPrestamo);
    public Page<Prestamo> buscarPrestamos(String busqueda, Boolean activo, Pageable  pageable);
    public Prestamo editarPrestamo(Long idPrestamo, PrestamoDTO prestamoDTO);
    public void finalizarPrestamo(LocalDate fechaDevolucion, Long idPrestamo);
    public void reabrirPrestamo(Long idPrestamo);
    public LocalDate sumarDiasHabiles(LocalDate FechaInicio);
}
