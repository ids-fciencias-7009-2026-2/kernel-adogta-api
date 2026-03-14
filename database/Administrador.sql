--Tabla Administrador
CREATE TABLE Administrador (
    id_administrador SERIAL PRIMARY KEY,
    email VARCHAR(150) NOT NULL,
    contraseña VARCHAR(30) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NULL

    --Revisar que el email tenga formato válido
    CONSTRAINT chk_email_formato CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$'),

    --Revisar la longitud mínima de la contraseña
    CONSTRAINT chk_contraseña_largo CHECK (length(contraseña) >= 8)
);