package com.examen.Examen2.controllers;

import com.examen.Examen2.entities.ClienteEntity;
import com.examen.Examen2.services.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Protección de sesión en todos los métodos
    private boolean sinSesion(HttpSession session) {
        return session.getAttribute("usuario") == null;
    }

    // Lista todos los clientes
    @GetMapping
    public String list(Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("clientes", clienteService.findAll());
        return "clientes";
    }

    // Muestra formulario para agregar
    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("cliente", new ClienteEntity());
        return "agregar-cliente";
    }

    // Guarda cliente nuevo
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ClienteEntity cliente, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        clienteService.save(cliente);
        return "redirect:/clientes";
    }

    // Muestra formulario para editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("cliente", clienteService.findById(id));
        return "editar-cliente";
    }

    // Actualiza cliente existente
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute ClienteEntity cliente, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        clienteService.save(cliente);
        return "redirect:/clientes";
    }

    // Muestra confirmación de eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("cliente", clienteService.findById(id));
        return "eliminar-cliente";
    }

    // Confirma y elimina
    @PostMapping("/eliminar/{id}")
    public String eliminarConfirmar(@PathVariable Long id, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        clienteService.delete(id);
        return "redirect:/clientes";
    }
}