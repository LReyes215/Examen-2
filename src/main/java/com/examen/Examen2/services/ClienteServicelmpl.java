package com.examen.Examen2.services;

import com.examen.Examen2.entities.ClienteEntity;
import com.examen.Examen2.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClienteServicelmpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<ClienteEntity> findAll() {
        return StreamSupport.stream(clienteRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteEntity findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ClienteEntity cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}