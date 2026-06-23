package com.examen.Examen2.repositories;

import com.examen.Examen2.entities.ServicioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<ServicioEntity,Long> {

}
