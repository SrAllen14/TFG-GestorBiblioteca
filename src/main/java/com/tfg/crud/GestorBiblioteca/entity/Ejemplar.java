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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa las personas y trabajadores de la biblioteca escolar
 * Contiene la información necesaria para realizar un préstamo y
 * autenticarse en la aplicación
 * 
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */

@Entity
@Table(name = "Ejemplares")
public class Ejemplar {
    /**
     * Número identificativo autogenerado incremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjemplar;
    
    /**
     * Cadena indentificativa compuesta por ISBN-IdEjemplar.
     */
    @Column(name = "Codigo")
    private String codigo;
    
    /**
     * Objeto de la clase libro que almacena la información.
     * del libro al que pertenece el ejemplar
     */
    @ManyToOne
    @JoinColumn(name = "idLibro")
    private Libro libro;

    /**
     * Estado actual del ejemplar que puede ser activo o inactivo
     */
    @Column(name = "Activo")
    private boolean activo;
    
    /**
     * Lista de préstamos en los que está el ejemplar
     */
    @OneToMany(mappedBy = "ejemplar")
    private List<Prestamo> prestamos;
    
    public Ejemplar() {
    }

    public Ejemplar(String codigo, String editorial, Libro libro, boolean activo) {
        this.codigo = codigo;
        this.libro = libro;
        this.activo = activo;
        this.prestamos = new ArrayList<>();
    }

    public Ejemplar(Long idEjemplar, String codigo, String editorial, Libro libro, boolean activo) {
        this.idEjemplar = idEjemplar;
        this.codigo = codigo;
        this.libro = libro;
        this.activo = activo;
        this.prestamos = new ArrayList<>();
    }

    public Long getIdEjemplar() {
        return idEjemplar;
    }

    public void setIdEjemplar(Long idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return "Ejemplar{" + "idEjemplar=" + idEjemplar + ", codigo=" + codigo + ", libro=" + libro + '}';
    }
    
    public Prestamo getPrestamoActivo(){
        
        if(prestamos == null){
            return null;
        }
        
        for(Prestamo prestamo: prestamos){
            if(prestamo.getFechaDevolucion() == null){
                return prestamo;
            }
        }
        
        return null;
    }
    
    public boolean isPrestado(){
        return getPrestamoActivo() != null;
    }
}
