/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.controller;

import com.tfg.crud.GestorBiblioteca.entity.Ejemplar;
import com.tfg.crud.GestorBiblioteca.entity.Genero;
import com.tfg.crud.GestorBiblioteca.entity.Libro;
import com.tfg.crud.GestorBiblioteca.service.EjemplarService;
import com.tfg.crud.GestorBiblioteca.service.LibroService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Usuario
 */
@Controller
@RequestMapping("/libro")
public class mtoLibrosController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private EjemplarService ejemplarService;

    @GetMapping
    public String mostrarLibros(Model modelo, @RequestParam(required = false) String busqueda, @RequestParam(required = false) String activo, @PageableDefault(size = 5) Pageable pageable) {

        Boolean activoFiltro = null;

        if ("true".equalsIgnoreCase(activo)) {
            activoFiltro = true;
        } else if ("false".equalsIgnoreCase(activo)) {
            activoFiltro = false;
        }

        Page<Libro> pagina = libroService.buscarLibros(busqueda, activoFiltro, pageable);
        
        modelo.addAttribute("pagina", pagina);
        modelo.addAttribute("libros", pagina.getContent());
        modelo.addAttribute("busqueda", busqueda);
        modelo.addAttribute("activo", activo);
        
        
        return "mtoLibros";
    }

    @GetMapping("/consultar/{idLibro}")
    public String consultarLibro(Model modelo, @PathVariable Long idLibro) {

        Libro libro = libroService.buscarLibroPorId(idLibro);
        List<Ejemplar> ejemplares = ejemplarService.listarEjemplaresPorLibro(idLibro);

        modelo.addAttribute("libro", libro);
        modelo.addAttribute("ejemplares", ejemplares);

        return "detalleLibro";
    }

    @GetMapping("/crear")
    public String mostrarRegistroLibro(Model modelo) {

        modelo.addAttribute("libro", new Libro());

        return "/registroLibro";
    }

    @PostMapping("/crear")
    public String registrarLibro(@Valid @ModelAttribute Libro libro, RedirectAttributes redirectAttributes) {

        try {
            libroService.registarLibro(libro);
            redirectAttributes.addFlashAttribute("success", "Usuario registrado");
        } catch (RuntimeException re) {
            redirectAttributes.addFlashAttribute("error", re.getMessage());
            return "redirect:/libro/crear";
        }

        return "redirect:/libro";
    }

    @GetMapping("/editar/{idLibro}")
    public String mostrarEditarLibro(Model modelo, @PathVariable Long idLibro) {

        Libro libro = libroService.buscarLibroPorId(idLibro);
        modelo.addAttribute("libro", libro);

        return "edicionLibro";
    }

    @PostMapping("/editar/{idLibro}")
    public String editarLibro(@Valid @PathVariable Long idLibro, @ModelAttribute Libro libro) {

        libroService.editarLibro(idLibro, libro);
        return "redirect:/libro";
    }

    @PostMapping("/estado/{idLibro}")
    public String cambiarEstadoLibro(@PathVariable Long idLibro) {

        libroService.modificarEstadoLibro(idLibro);
        return "redirect:/libro";
    }
    
    @GetMapping("/exportar")
    public void exportarLibros(HttpServletResponse response) throws IOException{
        
        List<Libro> libros = libroService.listarLibros();
        
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=libros.csv");
        
        PrintWriter writer = response.getWriter();
        
        writer.println("Titulo,Autor,ISBN,Genero,Editorial");
        
        for(Libro l : libros){
            writer.println(l.getTitulo() + ',' + l.getAutor() + ',' + l.getIsbn() + ',' + l.getGenero() + ',' + l.getEditorial());
        }
        
        writer.flush();
        writer.close();
    }
    
    @PostMapping("/importar")
    public String importarLibros(@RequestParam("archivo") MultipartFile archivo) throws IOException{
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
        String line;
        
        reader.readLine();
        
        while((line=reader.readLine()) != null){
            
            String[] data = line.split(",");
            
            Libro libro = new Libro();
            libro.setTitulo(data[0]);
            libro.setAutor(data[1]);
            libro.setIsbn(data[2]);
            libro.setGenero(Genero.valueOf(data[3]));
            libro.setEditorial(data[4]);
            libro.setActivo(true);
            
            libroService.registarLibro(libro);
        }
        
        return "redirect:/libro";
    }
}
