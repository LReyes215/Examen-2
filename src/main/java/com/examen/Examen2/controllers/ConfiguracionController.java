package com.examen.Examen2.controllers;

import com.examen.Examen2.entities.ConfiguracionEntity;
import com.examen.Examen2.services.ConfiguracionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configuracion")
public class ConfiguracionController {

    @Autowired
    private ConfiguracionService configuracionService;

    private boolean sinSesion(HttpSession session) {
        return session.getAttribute("usuario") == null;
    }

    @GetMapping
    public String ver(Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        model.addAttribute("config", configuracionService.get());
        return "configuracion";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ConfiguracionEntity config, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        configuracionService.save(config);
        return "redirect:/configuracion";
    }
}