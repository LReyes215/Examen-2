package com.examen.Examen2.services;

import com.examen.Examen2.entities.VentaEntity;
import java.util.List;

public interface VentaService {
    List<VentaEntity> findAll();
    VentaEntity findById(Long id);
    void save(VentaEntity venta);
    void delete(Long id);
}