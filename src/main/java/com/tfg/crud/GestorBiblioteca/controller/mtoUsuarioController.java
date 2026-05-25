/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.dto.UsuarioDTO;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import com.tfg.crud.GestorBiblioteca.service.UsuarioServiceImp;
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
@RequestMapping("/usuario")
public class mtoUsuarioController {
    
    @Autowired
    private UsuarioServiceImp usuarioService;
    
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
        modelo.addAttribute("libros", pagina.getContent());
        modelo.addAttribute("busqueda", busqueda);
        modelo.addAttribute("activo", activo);
        
        return "mtoUsuarios";
    }
    
    @GetMapping("/crear")
    public String mostrarRegistroUsuario(Model modelo){
        
        modelo.addAttribute("usuarioDTO", new UsuarioDTO());
        return "registroUsuario";
    }
    
    @PostMapping("/crear")
    public String registrarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, RedirectAttributes redirectAttributes){
        
        try{
            usuarioService.registrarUsuario(usuarioDTO);
            redirectAttributes.addFlashAttribute("succes","Usuario registrado correctamente");
        } catch(RuntimeException re){
            redirectAttributes.addFlashAttribute("error", re.getMessage());
            return "redirect:/usuario/crear";
        }
        
        return "redirect:/usuario";
        
    }
    
    @GetMapping("/editar/{idUsuario}")
    public String mostrarEditarUsuario(Model modelo, @PathVariable Long idUsuario){
        
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido1(usuario.getApellido1());
        usuarioDTO.setApellido2(usuario.getApellido2());
        usuarioDTO.setRol(usuario.getRol());
        usuarioDTO.setUsername(usuario.getUsername());
        
        modelo.addAttribute("usuarioDTO", usuarioDTO);
        modelo.addAttribute("idUsuario", idUsuario);
        
        return "edicionUsuario";
    }
    
    @PostMapping("/editar/{idUsuario}")
    public String editarUsuario(@PathVariable Long idUsuario, @ModelAttribute UsuarioDTO usuarioDTO){
        
        usuarioService.editarUsuario(idUsuario, usuarioDTO);
        return "redirect:/usuario";
    }
    
    @PostMapping("/estado/{idUsuario}")
    public String cambiarEstadoUsuario(@PathVariable Long idUsuario){
    
        usuarioService.modificarEstadoUsuario(idUsuario);
        return "redirect:/usuario";
    }
}
