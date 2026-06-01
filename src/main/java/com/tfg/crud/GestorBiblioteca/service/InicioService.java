package com.tfg.crud.GestorBiblioteca.service;

import com.tfg.crud.GestorBiblioteca.dto.InicioDTO;
import com.tfg.crud.GestorBiblioteca.entity.EstadoPrestamo;
import com.tfg.crud.GestorBiblioteca.entity.EstadoUsuario;
import com.tfg.crud.GestorBiblioteca.entity.Rol;
import com.tfg.crud.GestorBiblioteca.repository.LibroRepository;
import com.tfg.crud.GestorBiblioteca.repository.PrestamoRepository;
import com.tfg.crud.GestorBiblioteca.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio enecargado de obtener los datos principales mostrados por 
 * pantalla de inicio. Calcula estadísticas básicas como número de
 * préstamos activos, usuarios activos y libros disponibles.
 * 
 * @author alvaro.allper.1@educa.jcyl.es
 */
@Service
public class InicioService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    /**
     * Obtiene las métricas principales del sistema para la pantalla 
     * de inicio. Incluye préstamos activos, usuarios activos y libros 
     * activos.
     * 
     * @return objeto DTO con los datos resumidos del sistema.
     */
    public InicioDTO obtenerDatos() {

        InicioDTO dto = new InicioDTO();
        List<Rol> rolesPermitidos = List.of(Rol.ROLE_ALUMNO, Rol.ROLE_PROFESOR);
        
        dto.setPrestamosActivos(
            prestamoRepository.countByEstadoPrestamoIn(List.of(EstadoPrestamo.ACTIVO))
        );

        dto.setUsuariosActivos(
            usuarioRepository.countByEstadoUsuarioInAndTipoIn(List.of(EstadoUsuario.ACTIVO),rolesPermitidos)
        );

        dto.setLibrosActivos(
            libroRepository.countByActivoTrue()
        );

        return dto;
    }
}
