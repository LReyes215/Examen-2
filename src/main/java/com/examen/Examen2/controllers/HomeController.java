package com.examen.Examen2.controllers;

import com.examen.Examen2.entities.VentaEntity;
import com.examen.Examen2.services.ClienteService;
import com.examen.Examen2.services.ServicioService;
import com.examen.Examen2.services.VentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VentaService ventaService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/auth/login";
        }

        List<VentaEntity> ventas = ventaService.findAll();

        // Totales generales
        model.addAttribute("totalServicios", servicioService.findAll().size());
        model.addAttribute("totalClientes", clienteService.findAll().size());
        model.addAttribute("totalVentas", ventas.size());
        model.addAttribute("totalFacturas", ventas.size());

        // Ventas por mes (cantidad)
        int[] ventasPorMes = new int[12];
        // Ingresos por mes (total en dinero)
        double[] ingresosPorMes = new double[12];

        for (VentaEntity v : ventas) {
            if (v.getFechaVenta() != null) {
                int mes = v.getFechaVenta().getMonthValue() - 1; // 0-11
                ventasPorMes[mes]++;
                ingresosPorMes[mes] += v.getTotal();
            }
        }

        // Servicios más vendidos
        Map<String, Integer> serviciosContador = new LinkedHashMap<>();
        for (VentaEntity v : ventas) {
            servicioService.findAll().stream()
                    .filter(s -> s.getIdServicio().equals(v.getIdServicio()))
                    .findFirst()
                    .ifPresent(s -> {
                        serviciosContador.merge(s.getNombreServicio(), 1, Integer::sum);
                    });
        }

        // Ordena servicios de mayor a menor
        List<Map.Entry<String, Integer>> serviciosOrdenados = new ArrayList<>(serviciosContador.entrySet());
        serviciosOrdenados.sort((a, b) -> b.getValue() - a.getValue());

        List<String> nombresServicios = new ArrayList<>();
        List<Integer> cantidadesServicios = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : serviciosOrdenados) {
            nombresServicios.add(entry.getKey());
            cantidadesServicios.add(entry.getValue());
        }


        model.addAttribute("ventasPorMes", ventasPorMes); // Enviar el array directamente
        model.addAttribute("ingresosPorMes", ingresosPorMes); // Enviar el array directamente
        model.addAttribute("nombresServicios", nombresServicios); // Lista real, no String
        model.addAttribute("cantidadesServicios", cantidadesServicios); // Lista real, no String

        return "index";
    }
}