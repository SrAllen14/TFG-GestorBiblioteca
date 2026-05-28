/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.PrestamoDTO;
import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import com.tfg.crud.GestorBiblioteca.entity.EstadoPrestamo;
import com.tfg.crud.GestorBiblioteca.entity.EstadoUsuario;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import com.tfg.crud.GestorBiblioteca.repository.PrestamoRepository;
import com.tfg.crud.GestorBiblioteca.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class PrestamoServiceImp implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        prestamo.setEstadoPrestamo(EstadoPrestamo.ACTIVO);

        prestamoRepository.save(prestamo);

        return prestamo;
    }

    @Override
    public List<Prestamo> listarPrestamosPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            codigo = "";
        }

        return prestamoRepository.findByEjemplarCodigoContainingIgnoreCase(codigo);
    }

    @Override
    public Prestamo buscarPrestamoPorId(Long idPrestamo) {

        return prestamoRepository.findById(idPrestamo).orElseThrow(() -> new RuntimeException("No se ha encontrado el prestamo"));
    }

    @Override
    public Prestamo editarPrestamo(Long idPrestamo, PrestamoDTO prestamoDTO) {
        Prestamo prestamo = buscarPrestamoPorId(idPrestamo);
        Usuario usuario = usuarioService.buscarUsuarioPorId(prestamoDTO.getIdUsuario());
        Ejemplar ejemplar = ejemplarService.buscarEjemplarPorId(prestamoDTO.getIdEjemplar());

        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);

        return prestamoRepository.save(prestamo);
    }

    @Override
    public void finalizarPrestamo(LocalDate fechaDevolucion, Long idPrestamo, String codigo) {
        Prestamo prestamo = buscarPrestamoPorId(idPrestamo);

        if (codigo.equals(prestamo.getEjemplar().getCodigo())) {
            prestamo.setFechaDevolucion(fechaDevolucion);
            prestamo.setEstadoPrestamo(EstadoPrestamo.ENTREGADO);

            if (fechaDevolucion.isAfter(prestamo.getFechaFin())) {
                Usuario usuario = usuarioService.buscarUsuarioPorId(prestamo.getUsuario().getIdUsuario());

                usuario.setEstadoUsuario(EstadoUsuario.SUSPENDIDO);
                prestamo.setEstadoPrestamo(EstadoPrestamo.ENTREGADO_CON_RETRASO);
                usuarioRepository.save(usuario);
            }
            prestamoRepository.save(prestamo);
        }
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

        while (contador < 5) {
            fechaFin = fechaFin.plusDays(1);
            switch (fechaFin.getDayOfWeek()) {
                case SATURDAY:
                    break;
                case SUNDAY:
                    break;
                default:
                    contador++;
            }
        }

        return fechaFin;
    }

    @Override
    public void cambiarEstado(Long idPrestamo, EstadoPrestamo estadoPrestamo) {

        Prestamo prestamo = buscarPrestamoPorId(idPrestamo);

        prestamo.setEstadoPrestamo(estadoPrestamo);

        prestamoRepository.save(prestamo);
    }

    @Override
    public Page<Prestamo> buscarPrestamos(String busqueda, EstadoPrestamo estadoPrestamo, Pageable pageable) {

        return prestamoRepository.buscarPrestamos(busqueda, estadoPrestamo, pageable);
    }

    @Override
    public List<Prestamo> listarPrestamosPorUsuario(Long idUsuario) {
        return prestamoRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public Prestamo getPrestamoActivo(Ejemplar e) {
        return e.getPrestamos().stream().filter(p -> p.getEstadoPrestamo() == EstadoPrestamo.ACTIVO).findFirst().orElse(null);

    }

    @Override
    public void actualizarPrestamosRetrasados() {

        List<Prestamo> prestamosActivos = prestamoRepository.findAll();

        LocalDate hoy = LocalDate.now();

        for (Prestamo p : prestamosActivos) {

            if (p.getEstadoPrestamo() != EstadoPrestamo.ENTREGADO && p.getEstadoPrestamo() != EstadoPrestamo.ENTREGADO_CON_RETRASO
                    && hoy.isAfter(p.getFechaFin())) {

                p.setEstadoPrestamo(EstadoPrestamo.RETRASADO);
            }
            
            if (p.getEstadoPrestamo() != EstadoPrestamo.ENTREGADO && p.getEstadoPrestamo() != EstadoPrestamo.ENTREGADO_CON_RETRASO
                    && hoy.isBefore(p.getFechaFin())) {

                p.setEstadoPrestamo(EstadoPrestamo.ACTIVO);
            }
        }

        prestamoRepository.saveAll(prestamosActivos);
    }
}
