--Tabla usuario
CREATE TABLE Usuario (
    id_usuario SERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    googleID VARCHAR(255) UNIQUE NULL,
    contraseña VARCHAR(255) NOT NULL,
    acepta_terminos BOOLEAN NOT NULL,
    fecha_acepta_terminos DATE NOT NULL,
    codigo_postal VARCHAR(5) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    proovedor_autenticacion VARCHAR(50) NOT NULL,
    token_sesion VARCHAR(255) NOT NULL,
    fecha_expiracion_sesion TIMESTAMP NOT NULL,
    token_recuperacion_contraseña VARCHAR(255) NOT NULL,
    fecha_expiracion_token_recuperacion_contraseña TIMESTAMP NOT NULL,
    es_adoptante BOOLEAN NOT NULL,
    es_donante BOOLEAN NOT NULL,

    --Restricción para asegurar que un usuario sea al menos adoptante o donante
    CONSTRAINT chk_rol_usuario CHECK (es_adoptante = TRUE OR es_donante = TRUE),

    --Evitar cadenas vacías 
    CONSTRAINT chk_nombres_vacios CHECK (TRIM(nombres) <> ''),
    CONSTRAINT chk_paterno_vacio CHECK (TRIM(apellido_paterno) <> ''),

    --Validación básica de formato de email
    CONSTRAINT chk_email_formato CHECK (Email LIKE '%_@__%.__%'),

    --Validación de Código Postal (exactamente 5 dígitos numéricos)
    CONSTRAINT chk_cp_formato CHECK (codigo_postal ~ '^[0-9]{5}$'),

    --Validación de telefono
    CONSTRAINT chk_telefono_formato CHECK (telefono IS NULL OR telefono ~ '^\+?[0-9\s]+$'),

    --Longitud mínima de la contraseña 
    CONSTRAINT chk_contraseña_largo CHECK (length(contraseña) >= 8),

    --Validación de googleID
    CONSTRAINT chk_googleid_formato CHECK (googleID IS NULL OR googleID ~ '^[0-9]+$')
);
