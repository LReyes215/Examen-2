package com.examen.Examen2.repositories;

import com.examen.Examen2.entities.ClienteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteEntity, Long> {
}