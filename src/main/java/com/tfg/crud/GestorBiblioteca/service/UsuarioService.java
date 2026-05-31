package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.UsuarioDTO;
import com.tfg.crud.GestorBiblioteca.entity.EstadoUsuario;
import com.tfg.crud.GestorBiblioteca.entity.Usuario;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Servicio encargado de la gestión de usuarios del sistema.
 * Proporciona operaciones para crear, consultar, editar y
 * gestionar el estado de los usuarios.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
public interface UsuarioService{
    
    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param usuarioDTO datos del usuario a registrar
     * @return usuario creado
     */
    public Usuario registrarUsuario(UsuarioDTO usuarioDTO);
    
    /**
     * Busca un usuario por su identificador.
     * 
     * @param id identificador del usuario
     * @return usuario encontrado
     */
    public Usuario buscarUsuarioPorId(Long id);
    
    /**
     * Busca un usuario por su nombre de cuenta.
     * 
     * @param username nombre de cuenta del usuario
     * @return usuario encontrado
     */
    public Usuario buscarUsuarioPorUsername(String username);
    
    /**
     * Busca usuarios filtrando por nombre.
     * 
     * @param filtroNombre texto para filtrar usuarios
     * @return lista de usuarios que coinciden con el filtro
     */
    public List<Usuario> buscarUsuariosDisponibles(String filtroNombre);
    
    /**
     * Busca usuarios con filtros y paginación.
     * 
     * @param busqueda texto de búsqueda general
     * @param estadoUsuario estado del usuario
     * @param pageable configuración de paginación
     * @return página de usuarios encontrados
     */
    public Page<Usuario> buscarUsuarios(String busqueda, EstadoUsuario estadoUsuario, Pageable pageable);
    
    /**
     * Edita los datos de un usuario existente.
     * 
     * @param id identificador del usuario
     * @param usuarioEditadoDTO datos actualizados del usuario
     * @return usuario modificado
     */
    public Usuario editarUsuario(Long id, UsuarioDTO usuarioEditadoDTO);
    
    /**
     * Cambia el estado de un usuario.
     * 
     * @param id identificador del usuario
     * @param estadoUsuario nuevo estado del usuario
     */
    public void modificarEstadoUsuario(Long id, EstadoUsuario estadoUsuario);
}
