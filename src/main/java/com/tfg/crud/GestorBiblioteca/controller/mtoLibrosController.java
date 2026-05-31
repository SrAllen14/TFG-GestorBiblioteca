/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import com.tfg.crud.GestorBiblioteca.entity.Genero;
import com.tfg.crud.GestorBiblioteca.entity.Libro;
import com.tfg.crud.GestorBiblioteca.service.EjemplarService;
import com.tfg.crud.GestorBiblioteca.service.LibroService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador encargado de la gestión de libros y ejemplares.
 * Permite registrar, consultar, editar, importar y exportar
 * información relacionada con el catálogo de la biblioteca.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
@Controller
@RequestMapping("/libro")
public class mtoLibrosController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private EjemplarService ejemplarService;

    /**
     * Muestra el listado de libros con opciones de 
     * búsqueda, filtrado y paginación.
     * 
     * @param modelo Modelo utilizado para la vista
     * @param busqueda Texto de búsqueda
     * @param activo Estado del libro para filtrar
     * @param pageable Configuración de paginación
     * @return Vista de mantenimiento de libros
     */
    @GetMapping
    public String mostrarLibros(Model modelo, @RequestParam(required = false) String busqueda, @RequestParam(required = false) String activo, @PageableDefault(size = 5) Pageable pageable) {

        Boolean activoFiltro = null;

        if ("true".equalsIgnoreCase(activo)) {
            activoFiltro = true;
        } else if ("false".equalsIgnoreCase(activo)) {
            activoFiltro = false;
        }

        Page<Libro> pagina = libroService.buscarLibros(busqueda, activoFiltro, pageable);
        
        modelo.addAttribute("pagina", pagina);
        modelo.addAttribute("libros", pagina.getContent());
        modelo.addAttribute("busqueda", busqueda);
        modelo.addAttribute("activo", activo);
        
        
        return "mtoLibros";
    }

    /**
     * Muestra el detalle de un libro junto con sus 
     * ejemplares asociados.
     * 
     * @param modelo Modelo utilizado para la vista
     * @param idLibro Identificador del libro
     * @return Vista de detalle del libro
     */
    @GetMapping("/consultar/{idLibro}")
    public String consultarLibro(Model modelo, @PathVariable Long idLibro) {

        Libro libro = libroService.buscarLibroPorId(idLibro);
        List<Ejemplar> ejemplares = ejemplarService.listarEjemplaresPorLibro(idLibro);

        modelo.addAttribute("libro", libro);
        modelo.addAttribute("ejemplares", ejemplares);

        return "detalleLibro";
    }

    /**
     * Muestra el formulario de registro de libros.
     * 
     * @param modelo Modelo utilizado para la vista
     * @return Vista de registro de libro
     */
    @GetMapping("/crear")
    public String mostrarRegistroLibro(Model modelo) {

        modelo.addAttribute("libro", new Libro());

        return "registroLibro";
    }

    /**
     * Registra un nuevo libro en el sistema.
     * 
     * @param libro Datos del libro
     * @param result Resultado de las validaciones
     * @param redirectAttributes Atributos de redirección
     * @param modelo Modelo utilizado para la vista
     * @return Redirección a la ruta /libro o vista de mantenimiento
     */
    @PostMapping("/crear")
    public String registrarLibro(@Valid @ModelAttribute Libro libro, BindingResult result, RedirectAttributes redirectAttributes, Model modelo) {
        modelo.addAttribute("libro", libro);
        
        try{
            if(result.hasErrors()){
                return "registroLibro";
            } 

            libroService.registarLibro(libro);
            return "redirect:/libro";
        }catch(IllegalArgumentException ex){
            modelo.addAttribute("errorISBN", ex.getMessage());
            return "registroLibro";
        }    
    }

    /**
     * Muestra el formulario de edición de un libro.
     * 
     * @param modelo Modelo utilizado para la vista
     * @param idLibro Identificador del libro
     * @return Vista de edición de libro
     */
    @GetMapping("/editar/{idLibro}")
    public String mostrarEditarLibro(Model modelo, @PathVariable Long idLibro) {

        Libro libro = libroService.buscarLibroPorId(idLibro);
        modelo.addAttribute("libro", libro);

        return "edicionLibro";
    }

    /**
     * Actualiza la información de un libro existente.
     * 
     * @param idLibro Identificador del libro
     * @param libro Datos actualizados
     * @param result Resultado de las validaciones
     * @param redirectAttributes Atributos de redirección
     * @param modelo Modelo utilizado para la vista
     * @return Redirección a la ruta /libro o a la vista de mantenimiento
     */
    @PostMapping("/editar/{idLibro}")
    public String editarLibro(@PathVariable Long idLibro, @Valid @ModelAttribute Libro libro, BindingResult result, RedirectAttributes redirectAttributes, Model modelo) {
        
        modelo.addAttribute("libro", libro);
       
        try{
            if(result.hasErrors()){
                return "edicionLibro";
            } 

            libroService.editarLibro(idLibro, libro);
            return "redirect:/libro";
        }catch(IllegalArgumentException ex){
            modelo.addAttribute("errorISBN", ex.getMessage());
            return "edicionLibro";
        }   
    }

    /**
     * Modifica el estado de un libro.
     * 
     * @param idLibro Identificador del libro
     * @return Redirección a la ruta /libro o a la vista de mantenimiento
     */
    @PostMapping("/estado/{idLibro}")
    public String cambiarEstadoLibro(@PathVariable Long idLibro) {

        libroService.modificarEstadoLibro(idLibro);
        return "redirect:/libro";
    }
    
    /**
     * Exporta el catálogo de libros a un fichero CSV.
     * 
     * @param response Respuesta HTTP utilizada para generar el archivo
     * @throws IOException Si ocurre un error durante la exportación
     */
    @GetMapping("/exportar")
    public void exportarLibros(HttpServletResponse response) throws IOException{
        
        List<Libro> libros = libroService.listarLibros();
        
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=libros.csv");
        
        PrintWriter writer = response.getWriter();
        
        writer.println("Titulo,Autor,ISBN,Genero,Editorial");
        
        for(Libro l : libros){
            writer.println(l.getTitulo() + ',' + l.getAutor() + ',' + l.getIsbn() + ',' + l.getGenero() + ',' + l.getEditorial());
        }
        
        writer.flush();
        writer.close();
    }
    
    /**
     * Importa libros desde un fichero CSV.
     * 
     * @param archivo Archivo CSV seleccionado
     * @param redirectAttributes Atributos de redirección
     * @return Redirección a la ruta /libro o a la vista de mantenimiento
     * @throws IOException Si ocurre un error al leer el archivo
     */
    @PostMapping("/importar")
    public String importarLibros(@RequestParam("archivo") MultipartFile archivo, RedirectAttributes redirectAttributes) throws IOException{
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
            String line;

            reader.readLine();

            while((line=reader.readLine()) != null){

                String[] data = line.split(",");

                Libro libro = new Libro();
                libro.setTitulo(data[0]);
                libro.setAutor(data[1]);
                libro.setIsbn(data[2]);
                libro.setGenero(Genero.valueOf(data[3]));
                libro.setEditorial(data[4]);
                libro.setActivo(true);

                libroService.registarLibro(libro);
            }

            return "redirect:/libro";
        }catch(RuntimeException ex){
            redirectAttributes.addFlashAttribute("error", "No se han podido importar lo libros por este motivo: " + ex.getMessage());
            return "redirect:/libro";
        }
    }
    
    /**
     * Modifica el estado de un ejemplar asociado a un libro.
     * 
     * @param idLibro Identificador del libro
     * @param idEjemplar Identificador del ejemplar
     * @return Redirección a la ruta /libro/consultar/{idLibro} o vista de consulta de libro
     */
    @PostMapping("/consultar/{idLibro}/estado/{idEjemplar}")
    public String cambiarEstadoUsuario(@PathVariable Long idLibro, @PathVariable Long idEjemplar){
        
        ejemplarService.darDeBajaEjemplar(idEjemplar);
        return "redirect:/libro/consultar/" + idLibro;
    }
}
