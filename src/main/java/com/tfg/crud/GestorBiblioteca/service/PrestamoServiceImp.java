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
    public Prestamo registrarPrestamo(PrestamoDTO prestamoDTO) {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = sumarDiasHabiles(fechaInicio);
        
        Prestamo prestamo = new Prestamo();
        Usuario usuario = usuarioService.buscarUsuarioPorId(prestamoDTO.getIdUsuario());
        Ejemplar ejemplar = ejemplarService.buscarEjemplarPorId(prestamoDTO.getIdEjemplar());
        
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        
        prestamo.setFechaInicio(fechaInicio);
        prestamo.setFechaFin(fechaFin);
        prestamo.setFechaDevolucion(null);
        
        prestamoRepository.save(prestamo);
        
        return prestamo;
    }

    @Override
    public List<Prestamo> listarPrestamosPorCodigo(String codigo) {
        if(codigo == null || codigo.isBlank()){
            codigo = "";
        }
        
        return prestamoRepository.findByEjemplarCodigoContainingIgnoreCase(codigo);
    }

    @Override
    public Prestamo buscarPrestamoPorId(Long idPrestamo) {
        
        return prestamoRepository.findById(idPrestamo).orElseThrow(() -> new RuntimeException("No se ha encontrado el prestamo"));
    }
/*
    @Override
    public Prestamo editarPrestamo(Long idPrestamo, PrestamoDTO prestamoDTO) {
        Prestamo prestamo = buscarPrestamoPorId(idPrestamo);
        Usuario usuario = usuarioService.buscarUsuarioPorId(prestamoDTO.getIdUsuario());
        Ejemplar ejemplar = ejemplarService.buscarEjemplarPorId(prestamoDTO.getIdEjemplar());
        
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaFin(prestamoDTO.getFechaFin());
        
        
        
        return prestamoRepository.save(prestamo);
    }*/

    @Override
    public void finalizarPrestamo(LocalDate fechaDevolucion, Long idPrestamo) {
        Prestamo prestamo = buscarPrestamoPorId(idPrestamo);
        
        prestamo.setFechaDevolucion(fechaDevolucion);
        
        prestamoRepository.save(prestamo);
    }

    @Override
    public void reabrirPrestamo(Long idPrestamo) {
        Prestamo prestamo = buscarPrestamoPorId(idPrestamo);
        
        prestamo.setFechaDevolucion(null);
        
        prestamoRepository.save(prestamo);    
    }

    @Override
    public LocalDate sumarDiasHabiles(LocalDate fechaInicio) {

        LocalDate fechaFin = fechaInicio;
        int contador = 0;
        
        while(contador < 5){
            fechaFin = fechaFin.plusDays(1);
            switch (fechaFin.getDayOfWeek()){
                case SATURDAY:
                    break;
                case SUNDAY:
                    break;
                default: contador++;
            }
        }
        
        return fechaFin;
    }
    
}
