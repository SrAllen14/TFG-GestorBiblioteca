/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.dto.UsuarioDTO;
import com.tfg.crud.GestorBiblioteca.entity.EstadoUsuario;
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
 * Controlador encargado de la gestión de usuarios.
 * Permite consultar, registrar, editar y modificar
 * el estado de los usuarios de la aplicación.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
@Controller
@RequestMapping("/usuario")
public class mtoUsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PrestamoService prestamoService;
    
    /**
     * Muestra el matenimiento de usuarios con soporte
     * para búsqueda, filtrando y paginación.
     * 
     * @param modelo Modelo utilizado para enviar datos a la vista
     * @param busqueda Texto de búsqueda
     * @param estadoUsuario Estado por el que filtrar
     * @param pageable Configuración de usuarios
     * @return Vista de mantenimiento de usuarios
     */
    @GetMapping
    public String mostrarMtoUsuario(Model modelo, @RequestParam(required = false) String busqueda, @RequestParam(required = false)EstadoUsuario estadoUsuario, @PageableDefault(size = 5) Pageable pageable){
        
        Page<Usuario> pagina = usuarioService.buscarUsuarios(busqueda, estadoUsuario, pageable);
        
        modelo.addAttribute("pagina", pagina);
        modelo.addAttribute("usuarios", pagina.getContent());
        modelo.addAttribute("busqueda", busqueda);
        modelo.addAttribute("estadoUsuario", (estadoUsuario != null) ? estadoUsuario.name() : null);
        
        return "mtoUsuarios";
    }
    
    /**
     * Muestra el detalle de un usuario junto con sus préstamos 
     * asociados.
     * 
     * @param modelo Modelo utilizado para la vista
     * @param idUsuario Identificador del usuario
     * @return Vista de detalle del usuario
     */
    @GetMapping("/consultar/{idUsuario}")
    public String consultarUsuario(Model modelo, @PathVariable Long idUsuario) {

        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        List<Prestamo> prestamos = prestamoService.listarPrestamosPorUsuario(idUsuario);
        
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("prestamos", prestamos);

        return "detalleUsuario";
    }
    
    /**
     * Muestra el formulario de registro de usuarios.
     * 
     * @param modelo Modelo utilizado para la vista
     * @return Vista de registro de usuarios
     */
    @GetMapping("/crear")
    public String mostrarRegistroUsuario(Model modelo){
        
        modelo.addAttribute("usuarioDTO", new UsuarioDTO());
        return "registroUsuario";
    }
    
    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param usuarioDTO Datos introducidos en el formulario
     * @param result Resultado de las validaciones
     * @param redirectAttributes Atributos para mensajes temporales
     * @param modelo Modelo utilizado para la vista
     * @return Redirección a la ruta /usuario
     */
    @PostMapping("/crear")
    public String registrarUsuario(@Valid @ModelAttribute UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes redirectAttributes, Model modelo){
        modelo.addAttribute("usuarioDTO", usuarioDTO);
        
        try{
            if(result.hasErrors()){
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
    
    /**
     * Muestra el formulario de edición de un usuario.
     * 
     * @param modelo Modelo utilizado para la vista
     * @param idUsuario Identificador del usuario
     * @return Vista de edición
     */
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
    
    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param idUsuario Identificador del usuario
     * @param usuarioDTO Datos modificados
     * @param result Resultado de las validaciones
     * @param redirectAttributes Atributos de redirección
     * @param modelo Modelo utilizado para la vista
     * @return Redirección a la ruta /usuario
     */
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
    
    /**
     * Cambia el estado de un usuario a baja.
     * 
     * @param idUsuario Identificador del usuario
     * @return Redirección a la ruta /usuario
     */
    @PostMapping("/estado/baja/{idUsuario}")
    public String darDeBajaUsuario(@PathVariable Long idUsuario){
        
        usuarioService.modificarEstadoUsuario(idUsuario, EstadoUsuario.BAJA);
        return "redirect:/usuario";
    }
    
    /**
     * Reactiva un usuario dado de baja.
     * 
     * @param idUsuario Identificador del usuario
     * @return Redirección a la ruta /usuario
     */
    @PostMapping("/estado/alta/{idUsuario}")
    public String activarUsuario(@PathVariable Long idUsuario){
        
        usuarioService.modificarEstadoUsuario(idUsuario, EstadoUsuario.ACTIVO);
        return "redirect:/usuario";
    }
    
    /**
     * Muestra el formulario de edición de perfil del
     * usuario autenticado.
     * 
     * @param auth Información del usuario autenticado
     * @param modelo Modelo utilizado para la vista
     * @return Vista de edición de perfil
     */
    @GetMapping("/perfil")
    public String mostrarEditarPerfil(Authentication auth, Model modelo){
        String username = auth.getName();
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);
        
        usuarioDTO.setDni(usuario.getDni());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido1(usuario.getApellido1());
        usuarioDTO.setApellido2(usuario.getApellido2());
        usuarioDTO.setRol(usuario.getRol());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setPassword(usuario.getPassword());
        
        modelo.addAttribute("usuarioDTO", usuarioDTO);
        
        return "editarPerfil";
    }
    
    /**
     * Actualiza los datos del perfil del usuario autenticado.
     * 
     * @param usuarioDTO Datos modificados
     * @param result Resultado de las validaciones
     * @param redirectAttributes Atributos de redirección
     * @param modelo Modelo utilizado para la vista
     * @param auth Usuario autenticado
     * @param confirmPassword Confirmación de contraseña
     * @return Redirección a la ruta /inicio
     */
    @PostMapping("/perfil")
    public String editarPerfil(@Valid @ModelAttribute UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes redirectAttributes, Model modelo, Authentication auth, @RequestParam String confirmPassword){
        modelo.addAttribute("usuarioDTO", usuarioDTO);
        
        try{
            if(result.hasErrors()){
                return "edicionUsuario";
            }
            
            if(usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isBlank()){
                if(!usuarioDTO.getPassword().equals(confirmPassword)){
                    modelo.addAttribute("error", "Las contraseñas no coinciden");
                    return "editarPerfil";
                }
            }
            
            Usuario usuario = usuarioService.buscarUsuarioPorUsername(auth.getName());
            usuarioService.editarUsuario(usuario.getIdUsuario(), usuarioDTO);
            return "redirect:/";
        } catch(RuntimeException ex){
            modelo.addAttribute("error", ex.getMessage());
            return "editarPerfil";
        }
    }
}
