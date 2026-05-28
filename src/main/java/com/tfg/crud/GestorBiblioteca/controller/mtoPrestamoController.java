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
 *
 * @author Usuario
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
    
    @GetMapping("/consultar/{idPrestamo}")
    public String consultarPrestamo(@PathVariable Long idPrestamo, Model modelo){
        String sancion;
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(idPrestamo);
        
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

        boolean usuarioExiste = usuarios.stream()
                .anyMatch(u -> u.getIdUsuario().equals(prestamo.getUsuario().getIdUsuario()));

        if (!usuarioExiste) {
            usuarios.add(prestamo.getUsuario());
        }

        
        boolean ejemplarExiste = ejemplares.stream()
                .anyMatch(e -> e.getIdEjemplar().equals(prestamo.getEjemplar().getIdEjemplar()));

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

    @PostMapping("/editar/{idPrestamo}")
    public String editarPrestamo(@PathVariable Long idPrestamo, @ModelAttribute PrestamoDTO prestamoDTO) {

        prestamoService.editarPrestamo(idPrestamo, prestamoDTO);
        return "redirect:/prestamo";
    }

    @PostMapping("/finalizar/{idPrestamo}")
    public String modificarEstadoPrestamo(@PathVariable Long idPrestamo, @RequestParam String codigo) {

        LocalDate hoy = LocalDate.now();

        prestamoService.finalizarPrestamo(hoy, idPrestamo, codigo);

        return "redirect:/prestamo/consultar/{idPrestamo}";
    }
}
