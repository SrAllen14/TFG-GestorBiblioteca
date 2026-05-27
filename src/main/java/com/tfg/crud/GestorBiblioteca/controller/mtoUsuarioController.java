/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.dto.UsuarioDTO;
import com.tfg.crud.GestorBiblioteca.entity.Prestamo;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import com.tfg.crud.GestorBiblioteca.service.PrestamoService;
import com.tfg.crud.GestorBiblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/usuario")
public class mtoUsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PrestamoService prestamoService;
    
    @GetMapping
    public String mostrarMtoUsuario(Model modelo, @RequestParam(required = false) String busqueda, @RequestParam(required = false) String activo, @PageableDefault(size = 5) Pageable pageable){
    
        Boolean activoFiltro = null;

        if ("true".equalsIgnoreCase(activo)) {
            activoFiltro = true;
        } else if ("false".equalsIgnoreCase(activo)) {
            activoFiltro = false;
        }
        
        Page<Usuario> pagina = usuarioService.buscarUsuarios(busqueda, activoFiltro, pageable);
        
        modelo.addAttribute("pagina", pagina);
        modelo.addAttribute("usuarios", pagina.getContent());
        modelo.addAttribute("busqueda", busqueda);
        modelo.addAttribute("activo", activo);
        
        return "mtoUsuarios";
    }
    
    @GetMapping("/consultar/{idUsuario}")
    public String consultarLibro(Model modelo, @PathVariable Long idUsuario) {

        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        List<Prestamo> prestamos = prestamoService.listarPrestamosPorUsuario(idUsuario);
        
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("prestamos", prestamos);

        return "detalleUsuario";
    }
    
    @GetMapping("/crear")
    public String mostrarRegistroUsuario(Model modelo){
        
        modelo.addAttribute("usuarioDTO", new UsuarioDTO());
        return "registroUsuario";
    }
    
    @PostMapping("/crear")
    public String registrarUsuario(@Valid @ModelAttribute UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes redirectAttributes, Model modelo){
        modelo.addAttribute("usuarioDTO", usuarioDTO);
        
        try{
            if(result.hasErrors()){
                System.out.println("No funciona");
                return "registroUsuario";
            }
            usuarioService.registrarUsuario(usuarioDTO);
            redirectAttributes.addFlashAttribute("succes","Usuario registrado correctamente");
            return "redirect:/usuario";
        } catch(RuntimeException ex){
            modelo.addAttribute("error", ex.getMessage());
            return "registroUsuario";
        }
        
            
        
    }
    
    @GetMapping("/editar/{idUsuario}")
    public String mostrarEditarUsuario(Model modelo, @PathVariable Long idUsuario){
        
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        
        usuarioDTO.setDni(usuario.getDni());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido1(usuario.getApellido1());
        usuarioDTO.setApellido2(usuario.getApellido2());
        usuarioDTO.setRol(usuario.getRol());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setPassword(usuario.getPassword());
        
        modelo.addAttribute("usuarioDTO", usuarioDTO);
        modelo.addAttribute("idUsuario", idUsuario);
        
        return "edicionUsuario";
    }
    
    @PostMapping("/editar/{idUsuario}")
    public String editarUsuario(@PathVariable Long idUsuario, @Valid @ModelAttribute UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes redirectAttributes, Model modelo){
        modelo.addAttribute("usuarioDTO", usuarioDTO);
        try{
            if(result.hasErrors()){
                return "edicionUsuario";
            }
            usuarioService.editarUsuario(idUsuario, usuarioDTO);
            return "redirect:/usuario";
        } catch(RuntimeException ex){
            modelo.addAttribute("error", ex.getMessage());
            return "edicionUsuario";
        }
        
    }
    
    @PostMapping("/estado/{idUsuario}")
    public String cambiarEstadoUsuario(@PathVariable Long idUsuario){
        
        usuarioService.modificarEstadoUsuario(idUsuario);
        return "redirect:/usuario";
    }
    
    @GetMapping("/usuario/perfil")
    public String mostrarEditarPerfil(Authentication auth, Model modelo){
        String username = auth.getName();
        
        Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);
        
        modelo.addAttribute("usuario", usuario);
        return "editarPerfil";
    }
    
    @PostMapping("/usuario/perfil")
    public String editarPerfil(){
        
        
        return "redirect:/";
    }
}
