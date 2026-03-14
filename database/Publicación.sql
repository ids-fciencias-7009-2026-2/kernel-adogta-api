--Tabla Publicacion
CREATE TABLE Publicacion (
    id_publicacion SERIAL,
    id_usuario INT,
    estado VARCHAR(50),
    PRIMARY KEY (id_publicacion, id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),

    --Restringir los estados posibles de una publicación
    CONSTRAINT chk_estado_publicacion CHECK (estado IN ('Activa', 'Pausada', 'Cerrada', 'Borrada'))
);