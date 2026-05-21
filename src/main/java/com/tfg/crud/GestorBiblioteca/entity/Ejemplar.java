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
 *
 * @author Usuario
 */

@Entity
@Table(name = "Ejemplares")
public class Ejemplar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjemplar;
    
    @Column(name = "Codigo")
    private String codigo;
    
    @ManyToOne
    @JoinColumn(name = "idLibro")
    private Libro libro;

    @Column(name = "Activo")
    private boolean activo;
    
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
}
