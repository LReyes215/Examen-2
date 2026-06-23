package com.examen.Examen2.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("venta")
public class VentaEntity {

    @Id
    private Long idVenta;
    private Long idCliente;
    private Long idServicio;
    private LocalDate fechaVenta;
    private Integer cantidad;
    private Double total;
    private String direccion;
    private String descripcion;

    public Long getIdVenta() { return idVenta; }
    public void setIdVenta(Long idVenta) { this.idVenta = idVenta; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long idServicio) { this.idServicio = idServicio; }

    public LocalDate getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDate fechaVenta) { this.fechaVenta = fechaVenta; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}