CREATE TABLE Contrato (
    id_contrato SERIAL,
    fecha DATE,
    terminos TEXT,
    id_usuario_donante INT,
    id_usuario_adoptante INT,
    id_animal INT,
    id_publicacion_animal INT,
    id_usuario_animal INT,

    PRIMARY KEY (id_contrato, id_usuario_donante, id_usuario_adoptante, id_animal, id_publicacion_animal, id_usuario_animal),
    FOREIGN KEY (id_usuario_donante) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_usuario_adoptante) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_animal, id_publicacion_animal, id_usuario_animal) REFERENCES Animal(id_animal, id_publicacion, id_usuario),

    CONSTRAINT chk_donante_consistente CHECK (id_usuario_donante = id_usuario_animal)
);
