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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */

@Entity
@Table(name = "Usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @Column(name = "DNI", unique = true)
    private String dni;
    
    @Column(name = "Nombre")
    private String nombre;
    
    @Column(name = "Apellido1")
    private String apellido1;
    
    @Column(name = "Apellido2")
    private String apellido2;
    
    @Column(name = "Username")
    private String username;
            
    @Column(name = "Password", unique = true)
    private String password;
    
    @Column(name = "Rol")
    @Enumerated(EnumType.STRING)
    private Rol tipo;
    
    private boolean activo;

    @OneToMany(mappedBy = "usuario")
    private List<Prestamo> prestamos;
    
    public Usuario() {
    }

    public Usuario(String dni, String nombre, String apellido1, String apellido2, Rol tipo, boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.tipo = tipo;
        this.activo = activo;
        this.prestamos = new ArrayList<>();
    }

    public Usuario(String dni, String nombre, String apellido1, String apellido2, String username, String password, Rol tipo, boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.activo = activo;
        this.prestamos = new ArrayList<>();
    }

    public Usuario(Long idUsuario, String dni, String nombre, String apellido1, String apellido2, String password, Rol tipo, boolean activo) {
        this.idUsuario = idUsuario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.password = password;
        this.tipo = tipo;
        this.activo = activo;
        this.prestamos = new ArrayList<>();
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Rol getRol() {
        return tipo;
    }

    public void setRol(Rol tipo) {
        this.tipo = tipo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
