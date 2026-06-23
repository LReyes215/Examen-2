package com.examen.Examen2.repositories;

import com.examen.Examen2.entities.VentaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends CrudRepository<VentaEntity, Long> {
}