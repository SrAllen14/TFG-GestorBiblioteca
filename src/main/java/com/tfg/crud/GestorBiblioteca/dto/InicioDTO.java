/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.dto;

/**
 *
 * @author Usuario
 */
public class InicioDTO {
    private Long prestamosActivos;
    private Long usuariosActivos;
    private Long librosActivos;

    public InicioDTO() {
    }

    public InicioDTO(Long prestamosActivos, Long usuariosActivos, Long librosActivos) {
        this.prestamosActivos = prestamosActivos;
        this.usuariosActivos = usuariosActivos;
        this.librosActivos = librosActivos;
    }

    public Long getPrestamosActivos() {
        return prestamosActivos;
    }

    public void setPrestamosActivos(Long prestamosActivos) {
        this.prestamosActivos = prestamosActivos;
    }

    public Long getUsuariosActivos() {
        return usuariosActivos;
    }

    public void setUsuariosActivos(Long usuariosActivos) {
        this.usuariosActivos = usuariosActivos;
    }

    public Long getLibrosActivos() {
        return librosActivos;
    }

    public void setLibrosActivos(Long librosActivos) {
        this.librosActivos = librosActivos;
    }
    
    
}
