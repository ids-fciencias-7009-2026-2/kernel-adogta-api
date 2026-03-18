/*
============================================
ADOGTA DML
Kernel crew y la sonora dinamita
Datos de prueba
Versión: 1.0.0 | Iteración 1
============================================
*/

-- -------------------------------------------------------------
-- Usuario de prueba
-- Email:      admin@adogta.com
-- Contraseña: 1234  (SHA-256: 03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4)
-- Roles: adoptante y donante
-- -------------------------------------------------------------
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
    'admin',
    'howard',
    'benson',
    'admin@adogta.com',
    NULL,
    '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',
    TRUE,
    '2026-01-01',
    '06600',
    '5512345678',
    'local',
    NULL,
    NULL,
    NULL,
    NULL,
    TRUE,
    TRUE
);
