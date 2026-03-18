/*
============================================
ADOGTA DDL
Kernel crew y la sonora dinamita
Versión: 1.0.0 | Iteración 1
============================================
*/

-- Creación del esquema
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- -------------------------------------------------------------
-- Tabla: Usuario
-- -------------------------------------------------------------
CREATE TABLE Usuario (
    id_usuario                          SERIAL PRIMARY KEY,
    nombres                             VARCHAR(100) NOT NULL,
    apellido_paterno                    VARCHAR(100) NOT NULL,
    apellido_materno                    VARCHAR(100) NULL,
    email                               VARCHAR(255) UNIQUE NOT NULL,
    google_id                           VARCHAR(255) UNIQUE NULL,
    contrasena                          VARCHAR(255) NULL,
    acepta_terminos                     BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_acepta_terminos               DATE NULL,
    codigo_postal                       VARCHAR(5) NOT NULL,
    telefono                            VARCHAR(15) NULL,
    proveedor_autenticacion             VARCHAR(50) NOT NULL,
    token_sesion                        VARCHAR(255) NULL,
    fecha_expiracion_sesion             TIMESTAMP NULL,
    token_recuperacion_contrasena       VARCHAR(255) NULL,
    fecha_expiracion_token_recuperacion TIMESTAMP NULL,
    es_adoptante                        BOOLEAN NOT NULL DEFAULT FALSE,
    es_donante                          BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT chk_rol_usuario    CHECK (es_adoptante = TRUE OR es_donante = TRUE),
    CONSTRAINT chk_nombres_vacios CHECK (TRIM(nombres) <> ''),
    CONSTRAINT chk_paterno_vacio  CHECK (TRIM(apellido_paterno) <> ''),
    CONSTRAINT chk_email_formato  CHECK (email LIKE '%_@__%.__%'),
    CONSTRAINT chk_cp_formato     CHECK (codigo_postal ~ '^[0-9]{5}$'),
    CONSTRAINT chk_telefono_fmt   CHECK (telefono IS NULL OR telefono ~ '^\+?[0-9\s]+$'),
    CONSTRAINT chk_contrasena_len CHECK (contrasena IS NULL OR length(contrasena) >= 8),
    CONSTRAINT chk_googleid_fmt   CHECK (google_id IS NULL OR google_id ~ '^[0-9]+$')
);

COMMENT ON TABLE Usuario IS 'Entidad central del sistema. Representa a cualquier persona registrada en Adogta, ya sea adoptante, donante o ambos.';

COMMENT ON COLUMN Usuario.id_usuario                          IS 'Identificador único del usuario. Generado automáticamente por la base de datos.';
COMMENT ON COLUMN Usuario.nombres                             IS 'Nombre(s) del usuario. No puede ser vacío.';
COMMENT ON COLUMN Usuario.apellido_paterno                    IS 'Primer apellido del usuario. No puede ser vacío.';
COMMENT ON COLUMN Usuario.apellido_materno                    IS 'Segundo apellido del usuario. Opcional.';
COMMENT ON COLUMN Usuario.email                               IS 'Correo electrónico único del usuario. Se usa como credencial de acceso.';
COMMENT ON COLUMN Usuario.google_id                           IS 'Identificador de cuenta Google. NULL si el proveedor es local. Debe ser numérico.';
COMMENT ON COLUMN Usuario.contrasena                          IS 'Contraseña hasheada con SHA-256. NULL si el proveedor es Google. Mínimo 8 caracteres.';
COMMENT ON COLUMN Usuario.acepta_terminos                     IS 'Indica si el usuario aceptó los términos y condiciones.';
COMMENT ON COLUMN Usuario.fecha_acepta_terminos               IS 'Fecha en que se aceptaron los términos y condiciones. NULL hasta que el usuario los acepte.';
COMMENT ON COLUMN Usuario.codigo_postal                       IS 'Código postal del usuario. Exactamente 5 dígitos numéricos. Se usa para ubicación aproximada.';
COMMENT ON COLUMN Usuario.telefono                            IS 'Número telefónico del usuario. Opcional. Acepta formato internacional con prefijo +.';
COMMENT ON COLUMN Usuario.proveedor_autenticacion             IS 'Proveedor de autenticación del usuario: local (email/contraseña) o google (OAuth).';
COMMENT ON COLUMN Usuario.token_sesion                        IS 'Token de sesión activo generado al autenticarse. NULL cuando no hay sesión activa.';
COMMENT ON COLUMN Usuario.fecha_expiracion_sesion             IS 'Fecha y hora de expiración del token de sesión activo.';
COMMENT ON COLUMN Usuario.token_recuperacion_contrasena       IS 'Token temporal para el flujo de recuperación de contraseña. NULL cuando no hay recuperación activa.';
COMMENT ON COLUMN Usuario.fecha_expiracion_token_recuperacion IS 'Fecha y hora de expiración del token de recuperación de contraseña.';
COMMENT ON COLUMN Usuario.es_adoptante                        IS 'Indica si el usuario tiene habilitado el rol de adoptante.';
COMMENT ON COLUMN Usuario.es_donante                          IS 'Indica si el usuario tiene habilitado el rol de donante.';

COMMENT ON CONSTRAINT chk_rol_usuario    ON Usuario IS 'El usuario debe ser adoptante, donante, o ambos.';
COMMENT ON CONSTRAINT chk_nombres_vacios ON Usuario IS 'El campo nombres no puede ser una cadena vacía o solo espacios.';
COMMENT ON CONSTRAINT chk_paterno_vacio  ON Usuario IS 'El apellido paterno no puede ser una cadena vacía o solo espacios.';
COMMENT ON CONSTRAINT chk_email_formato  ON Usuario IS 'Validación básica de formato de correo electrónico.';
COMMENT ON CONSTRAINT chk_cp_formato     ON Usuario IS 'El código postal debe contener exactamente 5 dígitos numéricos.';
COMMENT ON CONSTRAINT chk_telefono_fmt   ON Usuario IS 'El teléfono debe contener solo dígitos, espacios o el prefijo +.';
COMMENT ON CONSTRAINT chk_contrasena_len ON Usuario IS 'La contraseña hasheada debe tener al menos 8 caracteres. NULL permitido para usuarios Google.';
COMMENT ON CONSTRAINT chk_googleid_fmt   ON Usuario IS 'El google_id debe ser numérico cuando no es NULL.';


-- -------------------------------------------------------------
-- Tabla: Administrador
-- -------------------------------------------------------------
CREATE TABLE Administrador (
    id_administrador SERIAL PRIMARY KEY,
    email            VARCHAR(150) NOT NULL,
    contrasena       VARCHAR(255) NOT NULL,
    nombres          VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NULL,

    CONSTRAINT chk_admin_email CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$'),
    CONSTRAINT chk_admin_pass  CHECK (length(contrasena) >= 8)
);

COMMENT ON TABLE Administrador IS 'Usuario con privilegios de moderación. Tiene acceso a funcionalidades restringidas como revisión de reportes y baneo de usuarios.';

COMMENT ON COLUMN Administrador.id_administrador IS 'Identificador único del administrador. Generado automáticamente por la base de datos.';
COMMENT ON COLUMN Administrador.email            IS 'Correo electrónico del administrador. Usado como credencial de acceso.';
COMMENT ON COLUMN Administrador.contrasena       IS 'Contraseña del administrador. Mínimo 8 caracteres.';
COMMENT ON COLUMN Administrador.nombres          IS 'Nombre(s) del administrador.';
COMMENT ON COLUMN Administrador.apellido_paterno IS 'Primer apellido del administrador.';
COMMENT ON COLUMN Administrador.apellido_materno IS 'Segundo apellido del administrador. Opcional.';

COMMENT ON CONSTRAINT chk_admin_email ON Administrador IS 'El email debe tener formato válido según expresión regular.';
COMMENT ON CONSTRAINT chk_admin_pass  ON Administrador IS 'La contraseña debe tener al menos 8 caracteres.';


-- -------------------------------------------------------------
-- Tabla: Raza
-- -------------------------------------------------------------
CREATE TABLE Raza (
    id_raza         SERIAL PRIMARY KEY,
    recomendaciones TEXT,
    info_general    TEXT,
    personalidad    TEXT
);

COMMENT ON TABLE Raza IS 'Catálogo de razas de perros y gatos. Su información puede complementarse con The Dog API y The Cat API.';

COMMENT ON COLUMN Raza.id_raza         IS 'Identificador único de la raza. Generado automáticamente por la base de datos.';
COMMENT ON COLUMN Raza.recomendaciones IS 'Recomendaciones de cuidado para la raza: alimentación, ejercicio, etc.';
COMMENT ON COLUMN Raza.info_general    IS 'Información general de la raza: origen, historia, características físicas.';
COMMENT ON COLUMN Raza.personalidad    IS 'Descripción del temperamento y personalidad típica de la raza.';


-- -------------------------------------------------------------
-- Tabla: Publicacion
-- -------------------------------------------------------------
CREATE TABLE Publicacion (
    id_publicacion SERIAL,
    id_usuario     INT,
    estado         VARCHAR(50),

    PRIMARY KEY (id_publicacion, id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),

    CONSTRAINT chk_estado_publicacion CHECK (estado IN ('Activa', 'Pausada', 'Cerrada', 'Borrada'))
);

COMMENT ON TABLE Publicacion IS 'Representa una oferta de adopción creada por un donante. Contiene el estado del proceso y está vinculada a un animal.';

COMMENT ON COLUMN Publicacion.id_publicacion IS 'Identificador de la publicación. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Publicacion.id_usuario     IS 'Usuario donante que creó la publicación. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Publicacion.estado         IS 'Estado actual de la publicación: Activa, Pausada, Cerrada o Borrada.';

COMMENT ON CONSTRAINT chk_estado_publicacion ON Publicacion IS 'Solo se permiten los estados definidos en el dominio del negocio: Activa, Pausada, Cerrada, Borrada.';


-- -------------------------------------------------------------
-- Tabla: Animal
-- -------------------------------------------------------------
CREATE TABLE Animal (
    id_animal         SERIAL,
    id_publicacion    INT,
    id_usuario        INT,
    nombre            VARCHAR(100),
    estado_vacunacion VARCHAR(50),
    esterilizado      BOOLEAN,
    descripcion       TEXT,
    entrenado         BOOLEAN,
    codigo_postal     VARCHAR(10),
    edad              INT,
    tipo              VARCHAR(6),
    id_raza           INT,

    PRIMARY KEY (id_animal, id_publicacion, id_usuario),
    FOREIGN KEY (id_publicacion, id_usuario) REFERENCES Publicacion(id_publicacion, id_usuario),
    FOREIGN KEY (id_raza) REFERENCES Raza(id_raza),

    CONSTRAINT chk_cp_animal     CHECK (codigo_postal ~ '^[0-9]{5}$'),
    CONSTRAINT chk_edad_positiva CHECK (edad >= 0),
    CONSTRAINT chk_tipo_animal   CHECK (tipo IN ('Perro', 'Gato')),
    CONSTRAINT chk_nombre_vacio  CHECK (TRIM(nombre) <> '')
);

COMMENT ON TABLE Animal IS 'Representa a la mascota registrada dentro de una publicación de adopción.';

COMMENT ON COLUMN Animal.id_animal         IS 'Identificador del animal. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Animal.id_publicacion    IS 'Publicación a la que pertenece este animal.';
COMMENT ON COLUMN Animal.id_usuario        IS 'Usuario dueño de la publicación.';
COMMENT ON COLUMN Animal.nombre            IS 'Nombre del animal dado por el donante.';
COMMENT ON COLUMN Animal.estado_vacunacion IS 'Estado de vacunación del animal: Completo, Parcial o Ninguno.';
COMMENT ON COLUMN Animal.esterilizado      IS 'Indica si el animal ha sido esterilizado.';
COMMENT ON COLUMN Animal.descripcion       IS 'Descripción libre del animal proporcionada por el donante.';
COMMENT ON COLUMN Animal.entrenado         IS 'Indica si el animal tiene algún tipo de entrenamiento básico.';
COMMENT ON COLUMN Animal.codigo_postal     IS 'Código postal de ubicación aproximada del animal.';
COMMENT ON COLUMN Animal.edad              IS 'Edad del animal en meses.';
COMMENT ON COLUMN Animal.tipo              IS 'Tipo de animal: Perro o Gato.';
COMMENT ON COLUMN Animal.id_raza           IS 'Referencia al catálogo de razas.';

COMMENT ON CONSTRAINT chk_cp_animal     ON Animal IS 'El código postal del animal debe tener exactamente 5 dígitos.';
COMMENT ON CONSTRAINT chk_edad_positiva ON Animal IS 'La edad del animal no puede ser negativa.';
COMMENT ON CONSTRAINT chk_tipo_animal   ON Animal IS 'Solo se aceptan los tipos Perro o Gato.';
COMMENT ON CONSTRAINT chk_nombre_vacio  ON Animal IS 'El nombre del animal no puede ser una cadena vacía o solo espacios.';


-- -------------------------------------------------------------
-- Tabla: Padecimientos
-- -------------------------------------------------------------
CREATE TABLE Padecimientos (
    id_animal      INT,
    id_publicacion INT,
    id_usuario     INT,
    padecimiento   VARCHAR(100),

    PRIMARY KEY (id_animal, id_publicacion, id_usuario, padecimiento),
    FOREIGN KEY (id_animal, id_publicacion, id_usuario)
        REFERENCES Animal(id_animal, id_publicacion, id_usuario)
);

COMMENT ON TABLE Padecimientos IS 'Lista de enfermedades o condiciones médicas de un animal. Relación multivaluada de Animal.';

COMMENT ON COLUMN Padecimientos.id_animal      IS 'Animal al que pertenece el padecimiento.';
COMMENT ON COLUMN Padecimientos.id_publicacion IS 'Publicación asociada al animal.';
COMMENT ON COLUMN Padecimientos.id_usuario     IS 'Usuario dueño de la publicación.';
COMMENT ON COLUMN Padecimientos.padecimiento   IS 'Nombre del padecimiento o condición médica, por ejemplo: Diabetes, Displasia.';


-- -------------------------------------------------------------
-- Tabla: Fotos
-- -------------------------------------------------------------
CREATE TABLE Fotos (
    id_animal      INT,
    id_publicacion INT,
    id_usuario     INT,
    foto           VARCHAR(255),

    PRIMARY KEY (id_animal, id_publicacion, id_usuario, foto),
    FOREIGN KEY (id_animal, id_publicacion, id_usuario)
        REFERENCES Animal(id_animal, id_publicacion, id_usuario)
);

COMMENT ON TABLE Fotos IS 'Lista de URLs o rutas de fotos asociadas a un animal. Relación multivaluada de Animal.';

COMMENT ON COLUMN Fotos.id_animal      IS 'Animal al que pertenece la foto.';
COMMENT ON COLUMN Fotos.id_publicacion IS 'Publicación asociada al animal.';
COMMENT ON COLUMN Fotos.id_usuario     IS 'Usuario dueño de la publicación.';
COMMENT ON COLUMN Fotos.foto           IS 'URL o ruta del archivo de la foto del animal.';


-- -------------------------------------------------------------
-- Tabla: Ban
-- -------------------------------------------------------------
CREATE TABLE Ban (
    id_ban           SERIAL,
    id_usuario       INT,
    por              VARCHAR(255),
    fecha            DATE,
    motivo           TEXT,
    id_administrador INT,

    PRIMARY KEY (id_ban, id_usuario),
    FOREIGN KEY (id_usuario)       REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_administrador) REFERENCES Administrador(id_administrador)
);

COMMENT ON TABLE Ban IS 'Registro de suspensiones aplicadas a usuarios por un administrador.';

COMMENT ON COLUMN Ban.id_ban           IS 'Identificador único del ban. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Ban.id_usuario       IS 'Usuario que recibió la suspensión.';
COMMENT ON COLUMN Ban.por              IS 'Nombre o referencia de quien aplicó el ban. Campo de auditoría libre.';
COMMENT ON COLUMN Ban.fecha            IS 'Fecha en que se aplicó el ban.';
COMMENT ON COLUMN Ban.motivo           IS 'Descripción detallada del motivo de la suspensión.';
COMMENT ON COLUMN Ban.id_administrador IS 'Administrador que aplicó la suspensión.';


-- -------------------------------------------------------------
-- Tabla: Solicitud
-- -------------------------------------------------------------
CREATE TABLE Solicitud (
    id_solicitud        SERIAL,
    id_usuario          INT,
    fecha               DATE,
    estado              VARCHAR(50),
    id_animal           INT,
    id_publicacion_animal INT,
    id_usuario_animal   INT,

    PRIMARY KEY (id_solicitud, id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_animal, id_publicacion_animal, id_usuario_animal)
        REFERENCES Animal(id_animal, id_publicacion, id_usuario),

    CONSTRAINT chk_estado_solicitud CHECK (estado IN ('Pendiente', 'Aprobada', 'Rechazada', 'Cancelada'))
);

COMMENT ON TABLE Solicitud IS 'Representa la intención de un usuario adoptante de adoptar un animal publicado. Vincula al adoptante con la publicación y el animal de interés.';

COMMENT ON COLUMN Solicitud.id_solicitud          IS 'Identificador de la solicitud. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Solicitud.id_usuario            IS 'Usuario adoptante que envió la solicitud. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Solicitud.fecha                 IS 'Fecha en que se registró la solicitud de adopción.';
COMMENT ON COLUMN Solicitud.estado                IS 'Estado actual de la solicitud: Pendiente, Aprobada, Rechazada o Cancelada.';
COMMENT ON COLUMN Solicitud.id_animal             IS 'Animal al que se dirige la solicitud.';
COMMENT ON COLUMN Solicitud.id_publicacion_animal IS 'Publicación en la que está registrado el animal.';
COMMENT ON COLUMN Solicitud.id_usuario_animal     IS 'Usuario donante dueño de la publicación del animal.';

COMMENT ON CONSTRAINT chk_estado_solicitud ON Solicitud IS 'Solo se permiten los estados definidos en el dominio del negocio: Pendiente, Aprobada, Rechazada, Cancelada.';


-- -------------------------------------------------------------
-- Tabla: Contrato
-- -------------------------------------------------------------
CREATE TABLE Contrato (
    id_contrato           SERIAL,
    fecha                 DATE,
    terminos              TEXT,
    id_usuario_donante    INT,
    id_usuario_adoptante  INT,
    id_animal             INT,
    id_publicacion_animal INT,
    id_usuario_animal     INT,

    PRIMARY KEY (id_contrato, id_usuario_donante, id_usuario_adoptante, id_animal, id_publicacion_animal, id_usuario_animal),
    FOREIGN KEY (id_usuario_donante)  REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_usuario_adoptante) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_animal, id_publicacion_animal, id_usuario_animal)
        REFERENCES Animal(id_animal, id_publicacion, id_usuario),

    CONSTRAINT chk_donante_consistente CHECK (id_usuario_donante = id_usuario_animal)
);

COMMENT ON TABLE Contrato IS 'Formaliza el acuerdo de adopción entre un donante y un adoptante para un animal específico. Se genera una vez que la solicitud es aprobada.';

COMMENT ON COLUMN Contrato.id_contrato           IS 'Identificador del contrato. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Contrato.fecha                 IS 'Fecha en que se formalizó el contrato de adopción.';
COMMENT ON COLUMN Contrato.terminos              IS 'Texto libre con los términos y condiciones acordados entre donante y adoptante.';
COMMENT ON COLUMN Contrato.id_usuario_donante    IS 'Usuario donante que cede al animal. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Contrato.id_usuario_adoptante  IS 'Usuario adoptante que recibe al animal. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Contrato.id_animal             IS 'Animal involucrado en el contrato. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Contrato.id_publicacion_animal IS 'Publicación en la que fue registrado el animal. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Contrato.id_usuario_animal     IS 'Usuario dueño de la publicación del animal. Parte de la llave primaria compuesta.';

COMMENT ON CONSTRAINT chk_donante_consistente ON Contrato IS 'El donante del contrato debe coincidir con el usuario dueño de la publicación del animal, garantizando consistencia referencial.';


-- -------------------------------------------------------------
-- Tabla: Reporte
-- -------------------------------------------------------------
CREATE TABLE Reporte (
    id_reporte       SERIAL,
    id_usuario       INT,
    id_publicacion   INT,
    estado           VARCHAR(50),
    fecha            DATE,
    motivo           TEXT,
    id_administrador INT,

    PRIMARY KEY (id_reporte, id_usuario, id_publicacion),
    FOREIGN KEY (id_publicacion, id_usuario) REFERENCES Publicacion(id_publicacion, id_usuario),
    FOREIGN KEY (id_administrador) REFERENCES Administrador(id_administrador),

    CONSTRAINT chk_estado_reporte CHECK (estado IN ('Pendiente', 'En Revisión', 'Atendido', 'Desestimado'))
);

COMMENT ON TABLE Reporte IS 'Registro de denuncias realizadas por usuarios sobre una publicación. Un administrador revisa y gestiona cada reporte.';

COMMENT ON COLUMN Reporte.id_reporte       IS 'Identificador del reporte. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Reporte.id_usuario       IS 'Usuario que realizó la denuncia. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Reporte.id_publicacion   IS 'Publicación sobre la que se realizó el reporte. Parte de la llave primaria compuesta.';
COMMENT ON COLUMN Reporte.estado           IS 'Estado actual del reporte: Pendiente, En Revisión, Atendido o Desestimado.';
COMMENT ON COLUMN Reporte.fecha            IS 'Fecha en que se registró el reporte.';
COMMENT ON COLUMN Reporte.motivo           IS 'Descripción detallada del motivo de la denuncia proporcionada por el usuario.';
COMMENT ON COLUMN Reporte.id_administrador IS 'Administrador asignado para revisar y resolver el reporte. NULL hasta que sea asignado.';

COMMENT ON CONSTRAINT chk_estado_reporte ON Reporte IS 'Solo se permiten los estados definidos en el dominio del negocio: Pendiente, En Revisión, Atendido, Desestimado.';
