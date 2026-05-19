/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.dto.UsuarioDTO;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import com.tfg.crud.GestorBiblioteca.service.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String mostrarMtoUsuario(Model modelo){
    
        modelo.addAttribute("usuarios", usuarioService.listarUsuarios());
        
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
    
    @GetMapping("/editar/{id}")
    public String mostrarEditarUsuario(Model modelo, @PathVariable Long id){
        
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        modelo.addAttribute("usuario", usuario);
        
        return "edicionUsuario";
    }
    
    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario){
        
        usuarioService.editarUsuario(id, usuario);
        return "redirect:/usuario";
    }
    
    @PostMapping("/estado/{id}")
    public String cambiarEstadoUsuario(@PathVariable Long id){
    
        usuarioService.modificarEstadoUsuario(id);
        return "redirect:/usuario";
    }
}
