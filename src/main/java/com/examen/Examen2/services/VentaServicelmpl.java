package com.examen.Examen2.services;

import com.examen.Examen2.entities.VentaEntity;
import com.examen.Examen2.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VentaServicelmpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<VentaEntity> findAll() {
        return StreamSupport.stream(ventaRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public VentaEntity findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public void save(VentaEntity venta) {
        ventaRepository.save(venta);
    }

    @Override
    public void delete(Long id) {
        ventaRepository.deleteById(id);
    }
}