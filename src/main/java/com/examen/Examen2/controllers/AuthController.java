package com.examen.Examen2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.examen.Examen2.entities.UserEntity;
import com.examen.Examen2.repositories.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }


    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/auth/login";
        }
        return "index";
    }


    @PostMapping("/login")
    public String loginPost(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "login";
        }

        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "Contraseña incorrecta");
            return "login";
        }

        session.setAttribute("usuario", user);
        return "redirect:/";
    }

    @PostMapping("/register")
    public String registerPost(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String nombre,
                               Model model) {

        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error", "El usuario ya existe");
            return "register";
        }

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(password);
        user.setNombre(nombre);

        userRepository.save(user);
        return "login";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        UserEntity user = userRepository.findByEmail(email);

        if (user != null) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("Recuperación de Contraseña - Plomería Reyes");
                message.setText("Hola " + user.getNombre() + ",\n\n" +
                        "Tu contraseña actual es: " + user.getPassword());

                mailSender.send(message);
                model.addAttribute("mensaje", "Se ha enviado un correo con tu contraseña.");
            } catch (Exception e) {
                model.addAttribute("error", "Error al enviar el correo: " + e.getMessage());
            }
        } else {
            model.addAttribute("error", "El correo no está registrado.");
        }
        return "forgot-password";
    }

    @GetMapping("/salir")
    public String salir(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}