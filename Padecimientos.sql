CREATE TABLE Padecimientos (
    id_animal INT,
    id_publicacion INT,
    id_usuario INT,
    padecimiento VARCHAR(100),

    PRIMARY KEY (id_animal, id_publicacion, id_usuario, padecimiento),
    FOREIGN KEY (id_animal, id_publicacion, id_usuario) REFERENCES Animal(id_animal, id_publicacion, id_usuario)
);

CREATE TABLE Fotos (
    id_animal INT,
    id_publicacion INT,
    id_usuario INT,
    foto VARCHAR(255),

    PRIMARY KEY (id_animal, id_publicacion, id_usuario, foto),
    FOREIGN KEY (id_animal, id_publicacion, id_usuario) REFERENCES Animal(id_animal, id_publicacion, id_usuario)
);
