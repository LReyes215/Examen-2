package com.examen.Examen2.controllers;

import com.examen.Examen2.entities.ServicioEntity;
import com.examen.Examen2.services.ServicioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    private boolean sinSesion(HttpSession session) {
        return session.getAttribute("usuario") == null;
    }

    // Lista todos los servicios
    @GetMapping
    public String list(Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("servicios", servicioService.findAll());
        return "servicios";
    }

    // Formulario nuevo servicio

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("servicio", new ServicioEntity()); // Esto vincula el th:object
        return "agregar-servicio"; // Debe ser el nombre exacto del archivo .html
    }

    // Guarda servicio nuevo
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ServicioEntity servicio, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        servicioService.save(servicio);
        return "redirect:/servicios";
    }

    // Formulario editar servicio
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("servicio", servicioService.findById(id));
        return "editar-servicio";
    }

    // Actualiza servicio
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute ServicioEntity servicio, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        servicioService.save(servicio);
        return "redirect:/servicios";
    }

    // Confirmación eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("servicio", servicioService.findById(id));
        return "eliminar-servicio";
    }

    // Confirma y elimina
    @PostMapping("/eliminar/{id}")
    public String eliminarConfirmar(@PathVariable Long id, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        servicioService.delete(id);
        return "redirect:/servicios";
    }
}