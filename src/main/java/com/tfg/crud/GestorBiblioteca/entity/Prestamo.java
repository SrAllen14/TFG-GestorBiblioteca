/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author Usuario
 */

@Entity
@Table(name = "Prestamos")
public class Prestamo {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long idPretamo;
    
    @Column(name = "FechaInicio")
    private LocalDate fechaPrestamo;
    
    @Column(name = "FechaFin")
    private LocalDate fechaFin;
    
    @Column(name = "fechaDevolucion")
    private LocalDate fechaDevolucion;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "idEjemplar")
    private Ejemplar ejemplar;

    public Prestamo() {
    }

    public Prestamo(LocalDate fechaPrestamo, LocalDate fechaFin, LocalDate fechaDevolucion, Usuario usuario, Ejemplar ejemplar) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaFin = fechaFin;
        this.fechaDevolucion = fechaDevolucion;
        this.usuario = usuario;
        this.ejemplar = ejemplar;
    }

    public Prestamo(long idPretamo, LocalDate fechaPrestamo, LocalDate fechaFin, LocalDate fechaDevolucion, Usuario usuario, Ejemplar ejemplar) {
        this.idPretamo = idPretamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaFin = fechaFin;
        this.fechaDevolucion = fechaDevolucion;
        this.usuario = usuario;
        this.ejemplar = ejemplar;
    }

    public long getIdPretamo() {
        return idPretamo;
    }

    public void setIdPretamo(long idPretamo) {
        this.idPretamo = idPretamo;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "idPretamo=" + idPretamo + ", fechaPrestamo=" + fechaPrestamo + ", fechaFin=" + fechaFin + ", fechaDevolucion=" + fechaDevolucion + ", usuario=" + usuario + ", ejemplar=" + ejemplar + '}';
    }
}
