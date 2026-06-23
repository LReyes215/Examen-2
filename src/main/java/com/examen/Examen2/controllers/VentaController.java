package com.examen.Examen2.controllers;

import com.examen.Examen2.entities.VentaEntity;
import com.examen.Examen2.services.ClienteService;
import com.examen.Examen2.services.ServicioService;
import com.examen.Examen2.services.VentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.examen.Examen2.entities.ServicioEntity;
import com.examen.Examen2.services.ServicioService;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ServicioService servicioService;


    private boolean sinSesion(HttpSession session) {
        return session.getAttribute("usuario") == null;
    }

    // Lista todas las ventas
    @GetMapping
    public String list(Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("ventas", ventaService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("servicios", servicioService.findAll());
        return "ventas";
    }

    // Formulario nueva venta
    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("venta", new VentaEntity());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("servicios", servicioService.findAll());
        return "agregar-venta";
    }

    // Guarda venta nueva
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute VentaEntity venta, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";

        // Calcula el total con el precio actual del servicio
        ServicioEntity servicio = servicioService.findById(venta.getIdServicio());
        if (servicio != null) {
            venta.setTotal(servicio.getPrecio() * venta.getCantidad());
        }

        ventaService.save(venta);
        return "redirect:/ventas";
    }

    // Formulario editar venta
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("venta", ventaService.findById(id));
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("servicios", servicioService.findAll());
        return "editar-venta";
    }

    // Actualiza venta
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute VentaEntity venta, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";

        // Recalcula el total con el precio actual del servicio
        ServicioEntity servicio = servicioService.findById(venta.getIdServicio());
        if (servicio != null) {
            venta.setTotal(servicio.getPrecio() * venta.getCantidad());
        }

        ventaService.save(venta);
        return "redirect:/ventas";
    }

    // Confirmación eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("venta", ventaService.findById(id));
        return "eliminar-venta";
    }

    // Confirma y elimina
    @PostMapping("/eliminar/{id}")
    public String eliminarConfirmar(@PathVariable Long id, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        ventaService.delete(id);
        return "redirect:/ventas";
    }
}