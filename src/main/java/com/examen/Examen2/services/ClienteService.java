package com.examen.Examen2.services;

import com.examen.Examen2.entities.ClienteEntity;
import java.util.List;

public interface ClienteService {
    List<ClienteEntity> findAll();
    ClienteEntity findById(Long id);
    void save(ClienteEntity cliente);
    void delete(Long id);
}