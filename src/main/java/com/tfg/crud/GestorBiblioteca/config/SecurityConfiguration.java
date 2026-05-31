/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de la aplicación mediante
 * Spring Security. Define las reglas de acceso, el inicio
 * de sesión y el cierre de sesión de los usuarios.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    
    /**
     * Configura las reglas de seguridad de la aplicación.
     * Establece las rutas públicas, el formulario de inicio 
     * de sesión y el proceso de cierre de sesión.
     * 
     * @param http Objeto de configuración de seguridad HTTP
     * @return Cadena de filtros de seguridad configurada
     * @throws Exception Si se produce un error duratne la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/login", "/api/inicio", "/css/**").permitAll().anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", false).failureUrl("/login?error=true").permitAll())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll());
        
        return http.build();
    }
    
    /**
     * Crea el codificador de contraseñas utilizado por la 
     * aplicación. Se emplea el algoritmo BCrypt para almacenar 
     * las contraseñas de forma segura.
     * 
     * @return Codificador de contraseñas BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
    
        return new BCryptPasswordEncoder();
    }
}
