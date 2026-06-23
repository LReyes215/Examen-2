package com.examen.Examen2.services;

import com.examen.Examen2.entities.ServicioEntity;
import com.examen.Examen2.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<ServicioEntity> findAll() {
        return StreamSupport.stream(serviceRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public ServicioEntity findById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ServicioEntity servicio) {
         serviceRepository.save(servicio);
    }

    @Override
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
