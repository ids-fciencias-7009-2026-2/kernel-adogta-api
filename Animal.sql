CREATE TABLE Animal (
    id_animal SERIAL,
    id_publicacion INT,
    id_usuario INT,
    nombre VARCHAR(100),
    estado_vacunacion VARCHAR(50),
    esterilizado BOOLEAN,
    descripcion TEXT,
    entrenado BOOLEAN,
    codigo_postal VARCHAR(10),
    edad INT,
    tipo VARCHAR(6),
    id_raza INT,

    PRIMARY KEY (id_animal, id_publicacion, id_usuario),
    FOREIGN KEY (id_publicacion, id_usuario) REFERENCES Publicacion(id_publicacion, id_usuario),
    FOREIGN KEY (id_raza) REFERENCES Raza(id_raza),

    CONSTRAINT chk_cp_animal CHECK (codigo_postal ~ '^[0-9]{5}$'),
    CONSTRAINT chk_edad_positiva CHECK (edad >= 0),
    CONSTRAINT chk_tipo_animal CHECK (tipo IN ('Perro', 'Gato')),
    CONSTRAINT chk_nombre_vacios CHECK (TRIM(nombre) <> '')
);
