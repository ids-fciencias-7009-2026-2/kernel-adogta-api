-- ============================================
-- DATOS DE PRUEBA: Usuario administrador
-- ============================================
-- Se inserta un usuario de prueba con ambos roles
-- (adoptante y donante) para poder verificar todos
-- los endpoints del sistema sin necesidad de registrar
-- un usuario nuevo manualmente.
--
-- Credenciales de acceso:
--   Email:      admin@adogta.com
--   Contraseña: 1234
--
-- NOTA SOBRE LA CONTRASEÑA:
--   Se almacena el hash SHA-256 de "1234", no el texto plano.
--   Hash: 03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4
--
-- NOTA SOBRE LOS TOKENS:
--   Los campos de token se insertan como NULL porque el usuario
--   aún no ha iniciado sesión. El token se genera dinámicamente
--   al hacer POST /usuarios/login.
-- ============================================
INSERT INTO Usuario (
    nombres,
    apellido_paterno,
    apellido_materno,
    email,
    google_id,
    contrasena,
    acepta_terminos,
    fecha_acepta_terminos,
    codigo_postal,
    telefono,
    proveedor_autenticacion,
    token_sesion,
    fecha_expiracion_sesion,
    token_recuperacion_contrasena,
    fecha_expiracion_token_recuperacion,
    es_adoptante,
    es_donante
) VALUES (
    -- Nombre del usuario de prueba
    'admin',

    -- Apellidos del usuario de prueba
    'howard',
    'benson',

    -- Correo usado para el login
    'admin@adogta.com',

    -- Sin cuenta de Google (proveedor local)
    NULL,

    -- Hash SHA-256 de la contraseña "1234"
    '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',

    -- Aceptó términos y condiciones
    TRUE,

    -- Fecha en que aceptó los términos
    '2026-01-01',

    -- Código postal de la CDMX (Doctores)
    '06600',

    -- Teléfono de prueba
    '5512345678',

    -- Autenticación mediante email y contraseña
    'local',

    -- Sin sesión activa al momento de la inserción
    NULL,
    NULL,

    -- Sin proceso de recuperación de contraseña activo
    NULL,
    NULL,

    -- Tiene ambos roles para facilitar las pruebas
    TRUE,
    TRUE
);