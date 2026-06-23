package com.examen.Examen2.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("configuracion")
public class ConfiguracionEntity {

    @Id
    private Long idConfig;
    private String nombreNegocio;
    private String direccion;
    private String telefono;
    private String email;

    public Long getIdConfig() { return idConfig; }
    public void setIdConfig(Long idConfig) { this.idConfig = idConfig; }

    public String getNombreNegocio() { return nombreNegocio; }
    public void setNombreNegocio(String nombreNegocio) { this.nombreNegocio = nombreNegocio; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}