package com.examen.Examen2.services;

import com.examen.Examen2.entities.ServicioEntity;
import java.util.List;

public interface ServicioService {
    List<ServicioEntity> findAll();
    ServicioEntity findById(Long id);
    void save(ServicioEntity servicio);
    void delete(Long id);
}