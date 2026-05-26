/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.entity;

import com.tfg.crud.GestorBiblioteca.validation.ISBN;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "Libros")
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;
    
    @Column(name = "Titulo")
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    @Column(name = "Autor")
    @NotBlank(message = "El autor es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El autor solo puede contener letras")
    private String autor;
    
    @Column(name = "Genero")
    @Enumerated(EnumType.STRING)
    private Genero genero;
    
    @Column(name = "Editorial")
    @NotBlank(message = "La editorial es obligatoria")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ,.]+$", message = "La editorial contiene caracteres no válidos")
    private String editorial;
    
    @Column(name = "ISBN", unique = true)
    @NotBlank(message = "El ISBN es obligatorio")
    @ISBN(message = "ISBN invalido")
    private String isbn;
    
    @Column(name = "Activo")
    private boolean activo;
    
    @OneToMany(mappedBy = "libro")
    private List<Ejemplar> ejemplares;
    
    public Libro() {
    }

    public Libro(String titulo, String autor, Genero genero, String editorial, String isbn, boolean activo) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.editorial = editorial;
        this.isbn = isbn;
        this.activo = activo;
        this.ejemplares = new ArrayList<>();
    }

    public Libro(Long idLibro, String titulo, String autor, Genero genero, String editorial, String isbn, boolean activo) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.editorial = editorial;
        this.isbn = isbn;
        this.activo = activo;
        this.ejemplares = new ArrayList<>();
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
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

    public List<Ejemplar> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(List<Ejemplar> ejemplares) {
        this.ejemplares = ejemplares;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return "Libro{" + "titulo=" + titulo + ", autor=" + autor + ", genero=" + genero + ", editorial=" + editorial + ", isbn=" + isbn + ", activo=" + activo + ", ejemplares=" + ejemplares + '}';
    }
}
