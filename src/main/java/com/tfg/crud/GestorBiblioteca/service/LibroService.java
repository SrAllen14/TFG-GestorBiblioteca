package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.entity.Libro;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Servicio encargado de la gestión de libros en la biblioteca.
 * Proporciona operaciones para crear, consultar, editar y
 * buscar libros.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
public interface LibroService{
    
    /**
     * Registra un nuevo libro en el sistema.
     * 
     * @param libro objeto libro a registrar
     * @return libro guardado
     */
    public Libro registarLibro(Libro libro);
    
    /**
     * Obtiene la lista de todos los libros registrados.
     * 
     * @return lista de libros
     */
    public List<Libro> listarLibros();
    
    /**
     * Busca un libro por su identificador.
     * 
     * @param id identificador del libro
     * @return libro encontrado
     */
    public Libro buscarLibroPorId(Long id);
    
    /**
     * Lista de libros disponibles filtrando por ISBN.
     * 
     * @param isbn código ISBN del libro
     * @return lista de libros disponibles
     */
    public List<Libro> listarLibrosDisponibles(String isbn);
    
    /**
     * Busca libros aplicando filtros y paginación.
     * 
     * @param busqueda texto de búsqueda
     * @param activo estado del libro
     * @param pageable configuración de paginación
     * @return página de libros encontrados
     */
    public Page<Libro> buscarLibros(String busqueda, Boolean activo, Pageable pageable);
    
    /**
     * Actualiza la información de un libro existente.
     * 
     * @param id identificador del libro
     * @param libro datos actualizados del libro
     * @return libro modificado
     */
    public Libro editarLibro(Long id, Libro libro);
    
    /**
     * Cambia el estado de un libro (activo/inactivo)
     * 
     * @param id identificador del libro
     */
    public void modificarEstadoLibro(Long id);
}
