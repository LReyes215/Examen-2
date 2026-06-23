package com.examen.Examen2.controllers;

import com.examen.Examen2.entities.ClienteEntity;
import com.examen.Examen2.entities.ServicioEntity;
import com.examen.Examen2.entities.VentaEntity;
import com.examen.Examen2.services.ClienteService;
import com.examen.Examen2.services.ConfiguracionService;
import com.examen.Examen2.services.ServicioService;
import com.examen.Examen2.services.VentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired private VentaService ventaService;
    @Autowired private ClienteService clienteService;
    @Autowired private ServicioService servicioService;
    @Autowired private ConfiguracionService configuracionService;

    private boolean sinSesion(HttpSession session) {
        return session.getAttribute("usuario") == null;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";

        List<VentaEntity> todasVentas = ventaService.findAll();
        List<ClienteEntity> clientes = clienteService.findAll();

        // Agrupa por idCliente + fecha
        Map<String, Map<String, Object>> grupos = new LinkedHashMap<>();

        for (VentaEntity v : todasVentas) {
            String clave = v.getIdCliente() + "_" + v.getFechaVenta().toString();

            if (!grupos.containsKey(clave)) {
                Map<String, Object> grupo = new LinkedHashMap<>();
                grupo.put("idCliente", v.getIdCliente());
                grupo.put("fecha", v.getFechaVenta().toString());
                grupo.put("cantidad", 0);
                grupo.put("total", 0.0);

                // Busca el nombre del cliente
                String nombreCliente = clientes.stream()
                        .filter(c -> c.getIdCliente().equals(v.getIdCliente()))
                        .map(ClienteEntity::getNombreCliente)
                        .findFirst().orElse("Desconocido");
                grupo.put("nombreCliente", nombreCliente);

                grupos.put(clave, grupo);
            }

            Map<String, Object> grupo = grupos.get(clave);
            grupo.put("cantidad", (int) grupo.get("cantidad") + 1);
            grupo.put("total", (double) grupo.get("total") + v.getTotal());
        }

        model.addAttribute("grupos", grupos.values());
        return "facturaciones";
    }

    @GetMapping("/imprimir/{id}")
    public String imprimir(@PathVariable Long id, Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";
        VentaEntity venta = ventaService.findById(id);
        ClienteEntity cliente = clienteService.findById(venta.getIdCliente());
        ServicioEntity servicio = servicioService.findById(venta.getIdServicio());
        model.addAttribute("venta", venta);
        model.addAttribute("cliente", cliente);
        model.addAttribute("servicio", servicio);
        model.addAttribute("config", configuracionService.get());
        return "imprimirFactura";
    }

    @GetMapping("/imprimir-dia/{idCliente}/{fecha}")
    public String imprimirDia(@PathVariable Long idCliente,
                              @PathVariable String fecha,
                              Model model, HttpSession session) {
        if (sinSesion(session)) return "redirect:/auth/login";

        LocalDate fechaVenta = LocalDate.parse(fecha);
        ClienteEntity cliente = clienteService.findById(idCliente);

        List<VentaEntity> ventasDelDia = ventaService.findAll().stream()
                .filter(v -> v.getIdCliente().equals(idCliente)
                        && v.getFechaVenta().equals(fechaVenta))
                .collect(Collectors.toList());

        Double totalGeneral = ventasDelDia.stream()
                .mapToDouble(VentaEntity::getTotal)
                .sum();

        List<ServicioEntity> serviciosDelDia = ventasDelDia.stream()
                .map(v -> servicioService.findById(v.getIdServicio()))
                .collect(Collectors.toList());

        model.addAttribute("cliente", cliente);
        model.addAttribute("fecha", fechaVenta);
        model.addAttribute("ventas", ventasDelDia);
        model.addAttribute("servicios", serviciosDelDia);
        model.addAttribute("totalGeneral", totalGeneral);
        model.addAttribute("config", configuracionService.get());
        return "imprimirFacturaDia";
    }
}