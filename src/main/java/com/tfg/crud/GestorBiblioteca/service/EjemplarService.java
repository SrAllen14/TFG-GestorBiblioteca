package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import java.util.List;

/**
 * Servicio encargado de la gestión de ejemplares de libros
 * en la biblioteca. Proporciona operaciones para registrar,
 * consultar, listar y dar de baja ejemplares.
 * 
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
public interface EjemplarService{
    
    /**
     * Registra un nuevo libro asociado a un libro.
     * 
     * @param idLibro identificador del libro al que pertenece el ejemplar
     * @return ejemplar registrado
     */
    public Ejemplar registrarEjemplar(Long idLibro);
    
    /**
     * Busca un ejemplar por su identificador.
     * 
     * @param id identificador del ejemplar
     * @return ejemplar encontrado
     */
    public Ejemplar buscarEjemplarPorId(Long id);
    
    /**
     * Obtiene todos los ejemplares registrados en el sistema.
     * 
     * @param idLibro identificador del libro
     * @return lista de ejemplares del libro
     */
    public List<Ejemplar> listarEjemplaresPorLibro(Long idLibro);
    
    /**
     * Obtiene todos los ejemplares registrados en el sistema.
     * 
     * @return lista de ejemplares
     */
    public List<Ejemplar> listarEjemplares();
    
    /**
     * Obtiene todos los ejemplares disponibles para préstamos.
     * 
     * @return lista de ejemplares disponibles
     */
    public List<Ejemplar> listarEjemplaresDisponibles();
    
    /**
     * Da de baja un ejemplar del sistema.
     * 
     * @param id identificador del ejemplar a dar de baja
     */
    public void darDeBajaEjemplar(Long id);
}
