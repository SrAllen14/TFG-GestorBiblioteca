package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.PrestamoDTO;
import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import com.tfg.crud.GestorBiblioteca.entity.EstadoPrestamo;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de la gestión de préstamos de la biblioteca.
 * Proporciona operaciones para crear, modificar, consultar y 
 * gestionar el estado de los préstamos.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */

@Service
public interface PrestamoService{
    
    /**
     * Registra un nuevo préstamo en el sistema.
     * 
     * @param prestamoDTO datos necesarios para crear el préstamo
     * @return préstamo registrado
     */
    public Prestamo registrarPrestamo(PrestamoDTO prestamoDTO);
    
    /**
     * Lista de todos los préstamoas asociados a un usuario.
     * 
     * @param idUsuario identificador del usuario
     * @return lista de préstamos del usuario
     */
    public List<Prestamo> listarPrestamosPorUsuario(Long idUsuario);
    
    /**
     * Busca préstamos por su identificador.
     * 
     * @param idPrestamo identificador del préstamo
     * @return préstamo encontrado
     */
    public Prestamo buscarPrestamoPorId(Long idPrestamo);
    
    /**
     * Busca préstamos aplicando filtros y paginación
     * 
     * @param busqueda texto de búsqueda
     * @param estadoPrestamo estado del préstamo
     * @param pageable configuración de paginación
     * @return página de préstamos encontrados
     */
    public Page<Prestamo> buscarPrestamos(String busqueda, EstadoPrestamo estadoPrestamo, Pageable  pageable);
    
    /**
     * Edita un préstamo existente.
     * 
     * @param idPrestamo identificador del préstamo
     * @param prestamoDTO datos actualizados del préstamo
     * @return préstamo modificado
     */
    public Prestamo editarPrestamo(Long idPrestamo, PrestamoDTO prestamoDTO);
    
    /**
     * Finaliza un préstamo estableciendo su fecha de devolución.
     * 
     * @param fechaDevolucion fecha en la que se devuelve el ejemplar
     * @param idPrestamo indentificador del préstamo
     * @param codigo código del ejemplar
     */
    public void finalizarPrestamo(LocalDate fechaDevolucion, Long idPrestamo, String codigo);
    
    /**
     * Reabre un préstamo previamente finalizado.
     * 
     * @param idPrestamo identificador del préstamo
     */
    public void reabrirPrestamo(Long idPrestamo);
    
    /**
     * Cambia el estado de un préstamo.
     * 
     * @param idPrestamo identificador del préstamo
     * @param estadoPrestamo nuevo estado del préstamo
     */
    public void cambiarEstado(Long idPrestamo, EstadoPrestamo estadoPrestamo);
    
    /**
     * Obtiene el préstamo activo asociado a un ejemplar.
     * 
     * @param e ejemplar consultado
     * @return préstamo activo si existe
     */
    public Prestamo getPrestamoActivo(Ejemplar e);
    
    /**
     * Calcula una fecha sumando días hábiles a una fecha inicial.
     * 
     * @param FechaInicio fecha de inicio
     * @return fecha resultante
     */
    public LocalDate sumarDiasHabiles(LocalDate FechaInicio);
    
    /**
     * Actualiza el estado de los préstamos retrasados en el sistema.
     */
    public void actualizarPrestamosRetrasados();
}
