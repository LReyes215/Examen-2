package com.examen.Examen2.services;

import com.examen.Examen2.entities.ConfiguracionEntity;

public interface ConfiguracionService {
    ConfiguracionEntity get();
    void save(ConfiguracionEntity config);
}