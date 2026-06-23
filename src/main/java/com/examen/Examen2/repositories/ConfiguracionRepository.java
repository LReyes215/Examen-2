package com.examen.Examen2.repositories;

import com.examen.Examen2.entities.ConfiguracionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionRepository extends CrudRepository<ConfiguracionEntity, Long> {
}