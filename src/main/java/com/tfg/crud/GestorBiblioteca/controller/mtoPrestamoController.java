/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.dto.PrestamoDTO;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import com.tfg.crud.GestorBiblioteca.service.EjemplarService;
import com.tfg.crud.GestorBiblioteca.service.LibroService;
import com.tfg.crud.GestorBiblioteca.service.PrestamoService;
import com.tfg.crud.GestorBiblioteca.service.UsuarioService;
import java.time.LocalDate;
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
    public String mostrarPrestamos(@RequestParam(required = false) String busqueda, @RequestParam(required = false) String activo,@PageableDefault(size = 10) Pageable pageable, Model model) {

        Boolean activoFiltro = null;

        if ("true".equals(activo)) {
            activoFiltro = true;
        } else if ("false".equals(activo)) {
            activoFiltro = false;
        }

        Page<Prestamo> pagina= prestamoService.buscarPrestamos(busqueda, activoFiltro, pageable);
        
        model.addAttribute("pagina", pagina);
        model.addAttribute("prestamos", pagina.getContent());
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("activo", activo);

        return "mtoPrestamos";
    }
    
    @GetMapping("/consultar/{idPrestamo}")
    public String consultarPrestamo(@PathVariable Long idPrestamo, Model modelo){
        
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(idPrestamo);
        modelo.addAttribute("prestamo", prestamo);
        
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
        
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(idPrestamo);

        PrestamoDTO prestamoDTO = new PrestamoDTO();

        prestamoDTO.setIdPrestamo(prestamo.getIdPrestamo());
        prestamoDTO.setIdEjemplar(prestamo.getEjemplar().getIdEjemplar());
        prestamoDTO.setIdUsuario(prestamo.getUsuario().getIdUsuario());

        modelo.addAttribute("ejemplares", ejemplarService.listarEjemplaresDisponibles());
        modelo.addAttribute("usuarios", usuarioService.buscarUsuariosDisponibles(nombre));

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
