CREATE TABLE IF NOT EXISTS `Usuario` (
                                         `id_usuario` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         `nombre` VARCHAR(150) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL
    );