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

-- -------------------------------------------------------------
-- Administradores de prueba
-- Email:      admin@adogta.com
-- Contraseña: 1234  (SHA-256: 03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4)
-- -------------------------------------------------------------
INSERT INTO administrador (email, contrasena, nombres, apellido_paterno)
VALUES 
    ('marco-rubio@adogta.com',   '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Marco',     'Rubio'),
    ('daniela@adogta.com',       '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Daniela',   'Flores'),
    ('patricio@adogta.com',      '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Patricio',  'Castillo'),
    ('luis@adogta.com',          '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Luis',      'Carrillo'),
    ('marco-raya@adogta.com',    '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Marco',     'Raya'),
    ('karla@adogta.com',         '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Karla',     'García');

-- -------------------------------------------------------------
-- Razas de prueba (10 perros, 10 gatos)
-- -------------------------------------------------------------
INSERT INTO Raza (nombre, tipo, talla, independencia, nivel_energia, personalidad, sociable_niños, sociable_mascotas, es_hipoalergenico) VALUES
('Sin Raza Especifica',          'Perro', 3, 3, 3, '',                                 3, 3, 0),
('Chihuahua',          'Perro', 1, 4, 4, 'Vivaz, alerta, valiente',                    2, 2, 0),
('Pastor Alemán',      'Perro', 5, 3, 5, 'Inteligente, protector y trabajador',        4, 3, 0),
('Poodle',             'Perro', 3, 3, 4, 'Listo, elegante, hipoalergénico',            5, 4, 1),
('Beagle',             'Perro', 2, 3, 4, 'Curioso y sociable',                         5, 5, 0),
('Labrador Retriever', 'Perro', 4, 2, 5, 'Amigable, leal y juguetón',                  5, 4, 0),
('Golden Retriever',   'Perro', 4, 2, 5, 'Enérgico, amistoso y confiable',             5, 4, 0),
('Schnauzer',          'Perro', 3, 3, 4, 'Amistoso, activo y listo',                   4, 4, 1),
('Bulldog Francés',    'Perro', 2, 4, 3, 'Gentil, afectuoso y tranquilo',              4, 3, 0),
('Border Collie',      'Perro', 4, 3, 5, 'Leal, obediente y activo',                   4, 4, 0),
('Bichón Frisé',       'Perro', 3, 3, 3, 'Juguetón, sociable y cariñoso',              5, 5, 1),
('Sin Raza Especifica',          'Gato', 3, 3, 3, '',                                 3, 3, 0),
('Persa',              'Gato',  2, 4, 2, 'Tranquilo, cariñoso, de bajo mantenimiento', 3, 3, 0),
('Siamés',             'Gato',  2, 3, 4, 'Vocal, curioso, muy social',                 4, 3, 0),
('Maine Coon',         'Gato',  4, 3, 3, 'Gentil gigante, amistoso',                   5, 4, 0),
('Británico',          'Gato',  2, 4, 3, 'Independiente, reservado y elegante',        2, 2, 0),
('Sphynx',             'Gato',  2, 3, 3, 'Cariñoso, tranquilo y adaptable',            4, 4, 1),
('Bengalí',            'Gato',  3, 4, 4, 'Curioso, activo, aventurero',                3, 4, 0),
('Ragdoll',            'Gato',  2, 3, 2, 'Suave, dócil y silencioso',                  5, 5, 0),
('Balinés',            'Gato',  2, 4, 3, 'Juguetón, inteligente y activo',             3, 3, 1),
('Bosque de Noruega',  'Gato',  3, 3, 3, 'Leal, calmado y afectuoso',                  4, 4, 0),
('Abisinio',           'Gato',  2, 4, 4, 'Enérgico, curioso y sociable',               4, 5, 0);