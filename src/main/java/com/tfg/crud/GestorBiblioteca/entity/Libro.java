/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "Libros")
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLibro;
    
    @Column(name = "Titulo")
    private String titulo;
    
    @Column(name = "Autor")
    private String autor;
    
    @Column(name = "Genero")
    @Enumerated(EnumType.STRING)
    private Genero genero;
    
    @Column(name = "Activo")
    private boolean activo;
    
    public Libro() {
    }

    public Libro(String titulo, String autor, Genero genero, boolean activo) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.activo = activo;
    }

    public Libro(long idLibro, String titulo, String autor, Genero genero, boolean activo) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.activo = activo;
    }

    public long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    @Override
    public String toString() {
        return "Libro{titulo=" + titulo + ", autor=" + autor + ", genero=" + genero + '}';
    }
}
