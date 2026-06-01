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
 * Representa las personas y trabajadores de la biblioteca escolar
 * Contiene la información necesaria para realizar un préstamo y
 * autenticarse en la aplicación
 * 
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */

@Entity
@Table(name = "Usuarios")
public class Usuario {
    
    /**
     * Número identificativo autogenerado incremental
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    /**
     * Número identificativo del Documento Nacional de Identidad español
     */
    @Column(name = "DNI", unique = true)
    private String dni;
    
    /**
     * Nombre del usuario
     */
    @Column(name = "Nombre")
    private String nombre;
    
    /**
     * Primer apellido del usuario
     */
    @Column(name = "Apellido1")
    private String apellido1;
    
    /**
     * Segundo apellido del usuario
     */
    @Column(name = "Apellido2")
    private String apellido2;
    
    /**
     * Nombre de usuario para usuarios del sistema
     */
    @Column(name = "Username")
    private String username;
      
    /**
     * Contraseña pertenciente a un username
     */
    @Column(name = "Password", unique = true)
    private String password;
    
    /**
     * Tipo de usuario que define su rol en el sistema
     */
    @Column(name = "Rol")
    @Enumerated(EnumType.STRING)
    private Rol tipo;
    
    /**
     * Estado en el que se encuentra el usuario
     */
    @Column(name = "EstadoUsuario")
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estadoUsuario;

    /**
     * Listado de préstamos realizados por un usuario
     */
    @OneToMany(mappedBy = "usuario")
    private List<Prestamo> prestamos;
    
    public Usuario() {
    }

    public Usuario(String dni, String nombre, String apellido1, String apellido2, Rol tipo, EstadoUsuario estadoUsuario) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.tipo = tipo;
        this.estadoUsuario = estadoUsuario;
        this.prestamos = new ArrayList<>();
    }

    public Usuario(String dni, String nombre, String apellido1, String apellido2, String username, String password, Rol tipo, EstadoUsuario estadoUsuario) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.estadoUsuario = estadoUsuario;
        this.prestamos = new ArrayList<>();
    }

    public Usuario(Long idUsuario, String dni, String nombre, String apellido1, String apellido2, String password, Rol tipo, EstadoUsuario estadoUsuario) {
        this.idUsuario = idUsuario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.password = password;
        this.tipo = tipo;
        this.estadoUsuario = estadoUsuario;        
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

    public EstadoUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}
