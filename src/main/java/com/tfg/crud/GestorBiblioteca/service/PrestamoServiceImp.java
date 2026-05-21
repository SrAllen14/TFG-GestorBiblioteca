/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.PrestamoDTO;
import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import com.tfg.crud.GestorBiblioteca.repository.PrestamoRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */

@Service
public class PrestamoServiceImp implements PrestamoService{
    
    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private EjemplarService ejemplarService;
    
    @Override
    public Prestamo registrarPretamo(PrestamoDTO prestamoDTO) {
        Prestamo prestamo = new Prestamo();
        Usuario usuario = usuarioService.buscarUsuarioPorId(prestamoDTO.getIdUsuario());
        Ejemplar ejemplar = ejemplarService.buscarEjemplarPorId(prestamoDTO.getIdEjemplar());
        
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        
        prestamo.setFechaInicio(prestamoDTO.getFechaInicio());
        prestamo.setFechaFin(prestamoDTO.getFechaFin());
        prestamo.setFechaDevolucion(null);
        
        prestamoRepository.save(prestamo);
        
        return prestamo;
    }

    @Override
    public List<Prestamo> listarPrestamos() {
        
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo buscarPrestamoPorId(Long idPrestamo) {
        
        return prestamoRepository.findById(idPrestamo).orElseThrow(() -> new RuntimeException("No se ha encontrado el prestamo"));
    }

    @Override
    public Prestamo editarPrestamo(PrestamoDTO prestamoDTO) {
        Prestamo prestamo = new Prestamo();
        
        return prestamo;
    }

    @Override
    public void finalizarPrestamo(LocalDate fechaDevolucion, Long idPrestamo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void reabrirPrestamo(Long idPrestamo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
