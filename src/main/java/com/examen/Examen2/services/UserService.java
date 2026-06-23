package com.examen.Examen2.services;

import com.examen.Examen2.entities.UserEntity;

public interface UserService {
    UserEntity save(UserEntity user);
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
}
