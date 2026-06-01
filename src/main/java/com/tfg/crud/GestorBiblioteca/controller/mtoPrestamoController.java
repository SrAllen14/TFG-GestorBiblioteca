/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.dto.PrestamoDTO;
import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import com.tfg.crud.GestorBiblioteca.entity.EstadoPrestamo;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import com.tfg.crud.GestorBiblioteca.service.EjemplarService;
import com.tfg.crud.GestorBiblioteca.service.LibroService;
import com.tfg.crud.GestorBiblioteca.service.PrestamoService;
import com.tfg.crud.GestorBiblioteca.service.UsuarioService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador encargado de la gestión de préstamos. 
 * Permite registrar, consultar, editar y finalizar
 * préstamos de ejemplares a los usuarios.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
@Controller
@RequestMapping("/prestamo")
public class mtoPrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    @Autowired
    private EjemplarService ejemplarService;

    /**
     * Muestra el listado de préstamos con opciones de 
     * búsqueda, filtrado y paginación.
     * 
     * @param busqueda Texto utilizado para la búsqueda
     * @param estadoPrestamo Estado por el que filtrar
     * @param pageable Configuración de paginación
     * @param model Modelo utilizado para la vista
     * @return Vista de mantenimiento de préstamos
     */
    @GetMapping
    public String mostrarPrestamos(@RequestParam(required = false) String busqueda, @RequestParam(required = false) EstadoPrestamo estadoPrestamo, @PageableDefault(size = 10) Pageable pageable, Model model) {
        
        Page<Prestamo> pagina= prestamoService.buscarPrestamos(busqueda, estadoPrestamo, pageable);
        
        prestamoService.actualizarPrestamosRetrasados();
        
        model.addAttribute("pagina", pagina);
        model.addAttribute("prestamos", pagina.getContent());
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("estadoPrestamo", (estadoPrestamo != null) ? estadoPrestamo.name() : null);

        return "mtoPrestamos";
    }
    
    /**
     * Muestra el detalle de un préstamo concreto.
     * 
     * @param idPrestamo Identificador del préstamo
     * @param modelo Modelo utilizado para la vista
     * @return Vista de detalle del préstamo
     */
    @GetMapping("/consultar/{idPrestamo}")
    public String consultarPrestamo(@PathVariable Long idPrestamo, Model modelo){
        String sancion;
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(idPrestamo);
        
        // Comprueba si la devolución se realizó fuera
        // de plazo para mostrar la información sobre
        // la sanción aplicada.
        
        if(prestamo.getFechaDevolucion() != null){
            if(prestamo.getFechaDevolucion().isAfter(prestamo.getFechaFin())){
                sancion = "El usuario ha sido suspendido por falta grave en la entrega del libro. Para terminar la suspensión dirigase al mantenimiento de usuario.";
            } else{
                sancion = null;
            }
        } else{
            sancion = null;
        }
        
        modelo.addAttribute("prestamo", prestamo);
        modelo.addAttribute("sancion", sancion);
        return "detallePrestamo";
    }

    /**
     * Muestra el formulario de registro de préstamos.
     * 
     * @param modelo Modelo utilizado para la vista
     * @param nombre Nombre utilizado para filtrar usuarios
     * @param isbn ISBN utilizado para filtrar libros
     * @param idEjemplar Ejemplar seleccionado
     * @param idUsuario Usuario seleccionado
     * @return Vista de registro de préstamo
     */
    @GetMapping("/registro")
    public String mostrarRegistroPrestamo(Model modelo, @RequestParam(required = false) String nombre, @RequestParam(required = false) String isbn, @RequestParam(required = false) Long idEjemplar, @RequestParam(required = false) Long idUsuario) {

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = prestamoService.sumarDiasHabiles(fechaInicio);

        PrestamoDTO prestamoDTO = new PrestamoDTO();

        prestamoDTO.setIdEjemplar(idEjemplar);
        prestamoDTO.setIdUsuario(idUsuario);
        prestamoDTO.setFechaInicio(fechaInicio);
        prestamoDTO.setFechaFin(fechaFin);

        modelo.addAttribute("prestamoDTO", prestamoDTO);

        modelo.addAttribute("ejemplares", ejemplarService.listarEjemplaresDisponibles());
        modelo.addAttribute("usuarios", usuarioService.buscarUsuariosDisponibles(nombre));
        
        modelo.addAttribute("fechaInicio", fechaInicio);
        modelo.addAttribute("fechaFin", fechaFin);
        
        
        modelo.addAttribute("nombreBuscado", nombre);
        modelo.addAttribute("isbnBuscado", isbn);

        return "registroPrestamo";
    }

    /**
     * Registro un nuevo préstamo en el sistema.
     * 
     * @param prestamoDTO Datos del préstamo
     * @param redirectAttributes Atributos para mensajes temporales
     * @return Redirección a la ruta /prestamo
     */
    @PostMapping("/registro")
    public String registrarPrestamo(@ModelAttribute PrestamoDTO prestamoDTO, RedirectAttributes redirectAttributes) {

        try {
            prestamoService.registrarPrestamo(prestamoDTO);
        } catch (RuntimeException re) {
            redirectAttributes.addFlashAttribute("error", re.getMessage());
            return "redirect:/prestamo/registro";
        }
        return "redirect:/prestamo";
    }

    /**
     * Muestra el formulario de edición de un préstamo.
     * 
     * @param modelo Modelo utilizado para la vista
     * @param idPrestamo Identificador del préstamo
     * @param nombre Nombre utilizado para filtrar usuarios
     * @param isbn ISBN utillizado para filtrar libros
     * @param idEjemplar Ejemplar seleccionado
     * @param idUsuario Usuario seleccionado
     * @return Vista de edición de préstamo
     */
    @GetMapping("/editar/{idPrestamo}")
    public String mostrarEditarPrestamo(Model modelo, @PathVariable Long idPrestamo, @RequestParam(required = false) String nombre, @RequestParam(required = false) String isbn, @RequestParam(required = false) Long idEjemplar, @RequestParam(required = false) Long idUsuario) {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = prestamoService.sumarDiasHabiles(fechaInicio);
        
        List<Ejemplar> ejemplares = new ArrayList<>(ejemplarService.listarEjemplaresDisponibles());
        List<Usuario> usuarios = new ArrayList<>(usuarioService.buscarUsuariosDisponibles(nombre));
        
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(idPrestamo);

        PrestamoDTO prestamoDTO = new PrestamoDTO();

        prestamoDTO.setIdPrestamo(prestamo.getIdPrestamo());
        prestamoDTO.setIdEjemplar(prestamo.getEjemplar().getIdEjemplar());
        prestamoDTO.setIdUsuario(prestamo.getUsuario().getIdUsuario());

        // Añade el usuario actual del préstamo a la lista
        // si no aparece entre los usuarios disponibles.
        boolean usuarioExiste = usuarios.stream().anyMatch(u -> u.getIdUsuario().equals(prestamo.getUsuario().getIdUsuario()));

        if (!usuarioExiste) {
            usuarios.add(prestamo.getUsuario());
        }

        // Añade el ejemplar el actual del préstamo a la lista
        // si no aparece entre los ejemplares disponibles.
        boolean ejemplarExiste = ejemplares.stream().anyMatch(e -> e.getIdEjemplar().equals(prestamo.getEjemplar().getIdEjemplar()));

        if (!ejemplarExiste) {
            ejemplares.add(prestamo.getEjemplar());
        }
        
        modelo.addAttribute("ejemplares", ejemplares);
        modelo.addAttribute("usuarios", usuarios);

        modelo.addAttribute("fechaInicio", fechaInicio);
        modelo.addAttribute("fechaFin", fechaFin);  
        
        modelo.addAttribute("prestamoDTO", prestamoDTO);

        return "edicionPrestamo";
    }

    /**
     * Actualiza la información de un préstamo existente.
     * 
     * @param idPrestamo Identificador del préstamo
     * @param prestamoDTO Datos actualizados
     * @return Redirección al listado de préstamos
     */
    @PostMapping("/editar/{idPrestamo}")
    public String editarPrestamo(@PathVariable Long idPrestamo, @ModelAttribute PrestamoDTO prestamoDTO) {

        prestamoService.editarPrestamo(idPrestamo, prestamoDTO);
        return "redirect:/prestamo";
    }

    /**
     * Finaliza un préstamo registrando la fecha de devolución.
     * 
     * @param idPrestamo Identificador del préstamo
     * @param codigo Código del ejemplar devuelto.
     * @return Redirección al detalle del préstamo
     */
    @PostMapping("/finalizar/{idPrestamo}")
    public String modificarEstadoPrestamo(@PathVariable Long idPrestamo, @RequestParam String codigo) {

        LocalDate hoy = LocalDate.now();

        prestamoService.finalizarPrestamo(hoy, idPrestamo, codigo);

        return "redirect:/prestamo/consultar/{idPrestamo}";
    }
}
