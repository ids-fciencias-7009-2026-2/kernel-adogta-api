--Tabla Ban
CREATE TABLE Ban (
    id_ban SERIAL,
    id_usuario INT,
    por VARCHAR(255),
    fecha DATE,
    motivo TEXT,
    id_administrador INT, 

    PRIMARY KEY (id_ban, id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_administrador) REFERENCES Administrador(id_administrador)
);