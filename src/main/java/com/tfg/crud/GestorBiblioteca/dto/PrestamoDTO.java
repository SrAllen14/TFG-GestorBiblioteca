/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.dto;

import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class PrestamoDTO {
    
    private Long idUsuario;
    private Long idEjemplar;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public PrestamoDTO() {
    }

    public PrestamoDTO(Long idUsuario, Long idEjemplar, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaDevolucion) {
        this.idUsuario = idUsuario;
        this.idEjemplar = idEjemplar;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdEjemplar() {
        return idEjemplar;
    }

    public void setIdEjemplar(String IdEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
