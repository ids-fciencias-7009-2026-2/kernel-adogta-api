CREATE TABLE Reporte (
    id_reporte SERIAL,
    id_usuario INT,
    id_publicacion INT,
    estado VARCHAR(50),
    fecha DATE,
    motivo TEXT,
    id_administrador INT,

    PRIMARY KEY (id_reporte, id_usuario, id_publicacion),
    FOREIGN KEY (id_publicacion, id_usuario) REFERENCES Publicacion(id_publicacion, id_usuario),
    FOREIGN KEY (id_administrador) REFERENCES Administrador(id_administrador),

    CONSTRAINT chk_estado_reporte CHECK (estado IN ('Pendiente', 'En Revisión', 'Atendido', 'Desestimado'))
);
