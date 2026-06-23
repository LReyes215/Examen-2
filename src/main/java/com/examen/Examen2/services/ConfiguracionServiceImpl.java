package com.examen.Examen2.services;

import com.examen.Examen2.entities.ConfiguracionEntity;
import com.examen.Examen2.repositories.ConfiguracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionServiceImpl implements ConfiguracionService {

    @Autowired
    private ConfiguracionRepository configuracionRepository;

    @Override
    public ConfiguracionEntity get() {
        // Siempre devuelve el primer y único registro
        return configuracionRepository.findById(1L).orElse(new ConfiguracionEntity());
    }

    @Override
    public void save(ConfiguracionEntity config) {
        configuracionRepository.save(config);
    }
}