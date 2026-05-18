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

/**
 *
 * @author Usuario
 */

@Entity
@Table(name = "Ejemplares")
public class Ejemplar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEjemplar;
    
    @Column(name = "Codigo")
    private String codigo;
    
    @Column(name = "Editorial")
    private String editorial;
    
    @ManyToOne
    @JoinColumn(name = "idLibro")
    private Libro libro;

    public Ejemplar() {
    }

    public Ejemplar(String codigo, String editorial, Libro libro) {
        this.codigo = codigo;
        this.editorial = editorial;
        this.libro = libro;
    }

    public Ejemplar(long idEjemplar, String codigo, String editorial, Libro libro) {
        this.idEjemplar = idEjemplar;
        this.codigo = codigo;
        this.editorial = editorial;
        this.libro = libro;
    }

    public long getIdEjemplar() {
        return idEjemplar;
    }

    public void setIdEjemplar(long idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "Ejemplar{" + "idEjemplar=" + idEjemplar + ", codigo=" + codigo + ", editorial=" + editorial + ", libro=" + libro + '}';
    }
}
