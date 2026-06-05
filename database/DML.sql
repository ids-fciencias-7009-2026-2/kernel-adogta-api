/*
============================================
ADOGTA DML
Kernel crew y la sonora dinamita
Datos de prueba
Versión: 2.0.0 | Iteración 4
============================================

Contraseña de prueba para todos los usuarios locales: 1234
SHA-256: 03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4
*/

-- ============================================================
-- DATOS EXISTENTES (preservados de v1.0.0)
-- ============================================================

-- Usuario base (adoptante + donante)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, google_id, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, token_sesion, fecha_expiracion_sesion, token_recuperacion_contrasena, fecha_expiracion_token_recuperacion, es_adoptante, es_donante, envio_formulario)
VALUES (1, 'admin', 'howard', 'benson', 'admin@adogta.com', NULL, '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2026-01-01', '06600', '5512345678', 'local', NULL, NULL, NULL, NULL, TRUE, TRUE, FALSE);

-- Administradores
INSERT INTO Administrador (id_administrador, email, contrasena, nombres, apellido_paterno) VALUES
       (1, 'marco-rubio@adogta.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Marco',    'Rubio'),
       (2, 'daniela@adogta.com',     '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Daniela',  'Flores'),
       (3, 'patricio@adogta.com',    '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Patricio', 'Castillo'),
       (4, 'luis@adogta.com',        '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Luis',     'Carrillo'),
       (5, 'marco-raya@adogta.com',  '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Marco',    'Raya'),
       (6, 'karla@adogta.com',       '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Karla',    'Sheridam');

-- Razas: 11 perros (id 1-11), 11 gatos (id 12-22)
INSERT INTO Raza (id_raza, nombre, tipo, talla, independencia, nivel_energia, personalidad, sociable_niños, sociable_mascotas, es_hipoalergenico) VALUES
(1,  'Sin Raza Especifica', 'Perro', 3, 3, 3, '',                                                  3, 3, 0),
(2,  'Chihuahua',           'Perro', 1, 4, 4, 'Vivaz, alerta, valiente',                           2, 2, 0),
(3,  'Pastor Alemán',       'Perro', 5, 3, 5, 'Inteligente, protector y trabajador',               4, 3, 0),
(4,  'Poodle',              'Perro', 3, 3, 4, 'Listo, elegante, hipoalergénico',                   5, 4, 1),
(5,  'Beagle',              'Perro', 2, 3, 4, 'Curioso y sociable',                                5, 5, 0),
(6,  'Labrador Retriever',  'Perro', 4, 2, 5, 'Amigable, leal y juguetón',                        5, 4, 0),
(7,  'Golden Retriever',    'Perro', 4, 2, 5, 'Enérgico, amistoso y confiable',                   5, 4, 0),
(8,  'Schnauzer',           'Perro', 3, 3, 4, 'Amistoso, activo y listo',                         4, 4, 1),
(9,  'Bulldog Francés',     'Perro', 2, 4, 3, 'Gentil, afectuoso y tranquilo',                    4, 3, 0),
(10, 'Border Collie',       'Perro', 4, 3, 5, 'Leal, obediente y activo',                         4, 4, 0),
(11, 'Bichón Frisé',        'Perro', 3, 3, 3, 'Juguetón, sociable y cariñoso',                     5, 5, 1),
(12, 'Sin Raza Especifica', 'Gato',  3, 3, 3, '',                                                  3, 3, 0),
(13, 'Persa',               'Gato',  2, 4, 2, 'Tranquilo, cariñoso, de bajo mantenimiento',       3, 3, 0),
(14, 'Siamés',              'Gato',  2, 3, 4, 'Vocal, curioso, muy social',                       4, 3, 0),
(15, 'Maine Coon',          'Gato',  4, 3, 3, 'Gentil gigante, amistoso',                         5, 4, 0),
(16, 'Británico',           'Gato',  2, 4, 3, 'Independiente, reservado y elegante',              2, 2, 0),
(17, 'Sphynx',              'Gato',  2, 3, 3, 'Cariñoso, tranquilo y adaptable',                  4, 4, 1),
(18, 'Bengalí',             'Gato',  3, 4, 4, 'Curioso, activo, aventurero',                      3, 4, 0),
(19, 'Ragdoll',             'Gato',  2, 3, 2, 'Suave, dócil y silencioso',                        5, 5, 0),
(20, 'Balinés',             'Gato',  2, 4, 3, 'Juguetón, inteligente y activo',                   3, 3, 1),
(21, 'Bosque de Noruega',   'Gato',  3, 3, 3, 'Leal, calmado y afectuoso',                        4, 4, 0),
(22, 'Abisinio',            'Gato',  2, 4, 4, 'Enérgico, curioso y sociable',                     4, 5, 0);


-- ============================================================
-- NUEVOS USUARIOS DE PRUEBA (ids 2-18)
-- Cubre: donantes puros, adoptantes puros, roles duales.
-- ============================================================

-- id=2  Sofía Martínez  — donante puro (3 publicaciones activas, 1 adicional)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (2, 'Sofía', 'Martínez', 'López', 'sofia.martinez@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-03-10', '06700', '5512341001', 'local', FALSE, TRUE, FALSE);

-- id=3  Carlos Mendoza  — adoptante + donante (ha adoptado Y publica animales)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (3, 'Carlos', 'Mendoza', 'Ríos', 'carlos.mendoza@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-04-01', '05000', '5598761002', 'local', TRUE, TRUE, TRUE);

-- id=4  Ana López  — adoptante + donante (publicación propia + solicitud aprobada en otro)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (4, 'Ana', 'López', 'Vega', 'ana.lopez@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-02-14', '03100', '5511112003', 'local', TRUE, TRUE, TRUE);

-- id=5  Roberto Sánchez  — donante puro (publicaciones pausada y cerrada)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (5, 'Roberto', 'Sánchez', 'Mora', 'roberto.sanchez@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-01-20', '06900', '5544443004', 'local', FALSE, TRUE, FALSE);

-- id=6  María García  — adoptante puro con ALERGIAS (formulario extremo: presupuesto bajo, soledad alta)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (6, 'María', 'García', 'Ruiz', 'maria.garcia@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-05-05', '06800', '5566665005', 'local', TRUE, FALSE, TRUE);

-- id=7  Jorge Herrera  — donante puro (publicación con adopción completada)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (7, 'Jorge', 'Herrera', 'Castillo', 'jorge.herrera@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2024-11-30', '04000', '5577776006', 'local', FALSE, TRUE, FALSE);

-- id=8  Lucía Torres  — donante puro (publicación activa con historial cancelado + publicación en proceso)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (8, 'Lucía', 'Torres', 'Blanco', 'lucia.torres@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-06-01', '06800', '5588887007', 'local', FALSE, TRUE, FALSE);

-- id=9  Miguel Ramírez  — adoptante puro (solicitudes: pendiente, cancelada, rechazada)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (9, 'Miguel', 'Ramírez', 'Jiménez', 'miguel.ramirez@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-04-18', '07000', '5599998008', 'local', TRUE, FALSE, TRUE);

-- id=10 Valentina Castro  — adoptante + donante multipropósito (publica Y adopta)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (10, 'Valentina', 'Castro', 'Olvera', 'valentina.castro@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-03-22', '07100', '5500009009', 'local', TRUE, TRUE, TRUE);

-- id=11 Eduardo Flores  — donante (usuario BANEADO, publicación suspendida)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (11, 'Eduardo', 'Flores', 'Mendez', 'eduardo.flores@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-02-01', '09000', '5511110010', 'local', FALSE, TRUE, FALSE);

-- id=12 Isabela Moreno  — adoptante puro (formulario: presupuesto alto, niños en casa)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (12, 'Isabela', 'Moreno', 'Reyes', 'isabela.moreno@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-05-14', '07100', '5522221011', 'local', TRUE, FALSE, TRUE);

-- id=13 Diego Vargas  — adoptante + donante (publica Schnauzer, solicita adoptar otro)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (13, 'Diego', 'Vargas', 'Salinas', 'diego.vargas@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-01-08', '04000', '5533332012', 'local', TRUE, TRUE, TRUE);

-- id=14 Fernanda Ríos  — donante puro (una publicación "En revision" + una activa)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (14, 'Fernanda', 'Ríos', 'Aguilar', 'fernanda.rios@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-07-01', '15000', '5544443013', 'local', FALSE, TRUE, FALSE);

-- id=15 Andrés Jiménez  — adoptante puro (formulario: presupuesto bajo, soledad máxima)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (15, 'Andrés', 'Jiménez', 'Peña', 'andres.jimenez@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-08-10', '09400', '5555554014', 'local', TRUE, FALSE, TRUE);

-- id=16 Paola Gutiérrez  — adoptante + donante, con alergias
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (16, 'Paola', 'Gutiérrez', 'Fuentes', 'paola.gutierrez@gmail.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-09-03', '01000', '5566665015', 'local', TRUE, TRUE, FALSE);


-- id=17 Alejandro Núñez  — donante puro (publicación activa + publicación borrada)
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (17, 'Alejandro', 'Núñez', 'Paredes', 'alejandro.nunez@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-10-01', '07200', '5577776016', 'local', FALSE, TRUE, FALSE);

-- id=18 Carolina Díaz  — adoptante puro, autenticación LOCAL, con alergias
INSERT INTO Usuario (id_usuario, nombres, apellido_paterno, apellido_materno, email, contrasena, acepta_terminos, fecha_acepta_terminos, codigo_postal, telefono, proveedor_autenticacion, es_adoptante, es_donante, envio_formulario)
VALUES (18, 'Carolina', 'Díaz', 'Lozano', 'carolina.diaz@gmail.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE, '2025-11-15', '16000', '5588887017', 'local', TRUE, FALSE, TRUE);



-- ============================================================
-- ESCENARIO A: Happy Path — Publicaciones ACTIVAS visibles en mapa
-- Cubre: listar publicaciones, mapa (CPs de CDMX reales),
--        recomendaciones por algoritmo K-radius, detalle de animal.
-- ============================================================

-- Publicacion 1 — usuario 2 (Sofía), Activa
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (1, 2, 'Activa');
-- Animal 1: Max, Labrador (energía 5, talla grande) — CP Doctores CDMX
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (1, 1, 2, 'Max', 'Completo', TRUE, 'Max es un Labrador cariñoso de año y medio. Le encanta jugar fetch y es excelente con los niños. Fue rescatado de la calle y lleva 6 meses en casa. Está vacunado, desparasitado y esterilizado. Busca una familia activa con espacio al aire libre.', TRUE, '06700', 18, 'Perro', 6, NULL, NULL, NULL, NULL);

-- Publicacion 2 — usuario 2 (Sofía), Activa
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (2, 2, 'Activa');
-- Animal 2: Luna, Persa (tranquila, baja energía) — CP Doctores CDMX
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (2, 2, 2, 'Luna', 'Completo', TRUE, 'Luna es una Persa adulta de 2 años, muy tranquila y cariñosa. Ideal para departamentos. Está esterilizada, con vacunas al día y acostumbrada a vivir en interiores. No le gustan los ambientes ruidosos. Perfecta para personas que trabajan desde casa.', FALSE, '06700', 24, 'Gato', 13, NULL, NULL, NULL, NULL);

-- Publicacion 3 — usuario 2 (Sofía), Activa — CASO: múltiples solicitudes pendientes
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (3, 2, 'Activa');
-- Animal 3: Rocky, Beagle (curioso, alta energía con niños) — CP Doctores CDMX
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (3, 3, 2, 'Rocky', 'Parcial', TRUE, 'Rocky es un Beagle de 1 año, lleno de energía y muy sociable. Le encanta explorar y es un compañero ideal para familias con niños. Le faltan dos vacunas pero ya está esterilizado. Necesita ejercicio diario y mucho amor.', TRUE, '06700', 12, 'Perro', 5, NULL, NULL, NULL, NULL);

-- Publicacion 4 — usuario 4 (Ana), Activa — publicación propia de usuaria multipropósito
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (4, 4, 'Activa');
-- Animal 4: Bella, Poodle (hipoalergénico, ideal para personas con alergias) — CP Narvarte CDMX
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (4, 4, 4, 'Bella', 'Completo', TRUE, 'Bella es una Poodle Toy de 3 años, hipoalergénica y perfectamente entrenada. Sabe sentarse, echarse y venir al llamado. Es ideal para personas con alergias a las mascotas. Ha vivido con niños pequeños toda su vida. Entrego su carpeta médica completa.', TRUE, '03100', 36, 'Perro', 4, NULL, NULL, NULL, NULL);

-- Publicacion 11 — usuario 10 (Valentina), Activa — usuaria multipropósito también dona
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (11, 10, 'Activa');
-- Animal 11: Nala, Border Collie con override_energia=3 (pereza atípica de raza)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (11, 11, 10, 'Nala', 'Completo', TRUE, 'Nala es una Border Collie de 14 meses que, contrario a su raza, es bastante tranquila y sedentaria. Disfruta paseos cortos y largas siestas. Se lleva bien con otros perros y es gentil con niños. Busca un hogar calmado donde sea la consentida.', TRUE, '07100', 14, 'Perro', 10, 3, NULL, NULL, NULL);

-- Publicacion 13 — usuario 13 (Diego), Activa — donante + adoptante simultáneo
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (13, 13, 'Activa');
-- Animal 13: Titán, Schnauzer (hipoalergénico)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (13, 13, 13, 'Titán', 'Completo', TRUE, 'Titán es un Schnauzer Miniatura de 20 meses. Hipoalergénico, listo y muy amigable. Sabe las órdenes básicas y convive bien con niños y otros perros. Busco nuevo hogar por mudanza al extranjero. Incluyo todos sus accesorios y su historial médico.', TRUE, '04000', 20, 'Perro', 8, NULL, NULL, NULL, NULL);

-- Publicacion 14 — usuario 16 (Paola), Activa — Sphynx hipoalergénico
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (14, 16, 'Activa');
-- Animal 14: Isis, Sphynx (hipoalergénico, no le gustan otras mascotas — override)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (14, 14, 16, 'Isis', 'Completo', TRUE, 'Isis es una Sphynx de 16 meses, sin pelo y completamente hipoalergénica. Es muy cariñosa con las personas pero prefiere ser la única mascota del hogar. Necesita temperatura cálida. Perfecta para quien quiera compañía constante sin alergias.', FALSE, '01000', 16, 'Gato', 17, NULL, NULL, NULL, 2);


-- Publicacion 15 — usuario 17 (Alejandro), Activa — Border Collie independiente
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (15, 17, 'Activa');
-- Animal 15: Rex, Border Collie con override_independencia=4
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (15, 15, 17, 'Rex', 'Completo', TRUE, 'Rex es un Border Collie de 3 años muy inteligente e independiente. Sabe más de 15 comandos y puede estar solo varios horas sin problema. Ideal para personas activas pero ocupadas. Ha participado en competencias de agility. Incluyo sus trofeos y su historial de entrenamiento.', TRUE, '07200', 36, 'Perro', 10, NULL, 4, NULL, NULL);

-- Publicacion 17 — usuario 4 (Ana), Activa — segunda publicación del usuario multipropósito
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (17, 4, 'Activa');
-- Animal 17: Coco, Bulldog Francés (tranquilo, bueno para departamento)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (17, 17, 4, 'Coco', 'Completo', TRUE, 'Coco es un Bulldog Francés de 3.5 años, apacible y perfecto para departamento. No necesita mucho ejercicio y duerme la mayoría del tiempo. Se lleva bien con niños mayores y es muy afectuoso. Cedo por cambio de estilo de vida. Entrego cama, juguetes y cartilla médica.', FALSE, '03100', 42, 'Perro', 9, NULL, NULL, NULL, NULL);

-- Publicacion 18 — usuario 2 (Sofía), Activa — Bichón Frisé hipoalergénico (4a publicación de Sofía)
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (18, 2, 'Activa');
-- Animal 18: Lola, Bichón Frisé (hipoalergénico, ideal para personas con alergias)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (18, 18, 2, 'Lola', 'Completo', TRUE, 'Lola es una Bichón Frisé de 8 meses, hipoalergénica y llena de energía. Es muy sociable con personas, niños y otras mascotas. Apenas comenzando entrenamiento pero aprende rápido. Busca una familia paciente y cariñosa que le dé tiempo y atención.', FALSE, '06700', 8, 'Perro', 11, NULL, NULL, NULL, NULL);

-- Publicacion 19 — usuario 14 (Fernanda), Activa — Abisinio con diversidad geográfica
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (19, 14, 'Activa');
-- Animal 19: Mango, Abisinio (energético, curioso, zona Venustiano Carranza)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (19, 19, 14, 'Mango', 'Parcial', TRUE, 'Mango es un Abisinio de 10 meses lleno de vida y curiosidad. Explora cada rincón de la casa y le encanta trepar. Convive bien con otros gatos pero requiere mucha estimulación. Está en proceso de vacunación. Ideal para hogar activo y con espacio vertical.', FALSE, '15000', 10, 'Gato', 22, NULL, NULL, NULL, NULL);

-- Publicacion 21 — usuario 3 (Carlos), Activa — el adoptante+donante también publica
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (21, 3, 'Activa');
-- Animal 21: Pepita, gato sin raza definida (caso: raza genérica, rescatada de calle) — CP Cuajimalpa
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (21, 21, 3, 'Pepita', 'Parcial', TRUE, 'Pepita es una gatita callejera rescatada de apenas 6 meses. Fue encontrada bajo la lluvia y rehabilitada durante 2 meses. Ya está esterilizada, desparasitada y con primeras vacunas. Es tímida al principio pero muy cariñosa una vez que confía. Busca un hogar paciente.', FALSE, '05000', 6, 'Gato', 12, NULL, NULL, NULL, NULL);


-- ============================================================
-- ESCENARIO B: Adopción EN PROCESO
-- Publicación cambia a "En proceso", solicitud aprobada y otras rechazadas.
-- ============================================================

-- Publicacion 5 — usuario 4 (Ana), EN PROCESO — Poodle con tramite activo
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (5, 4, 'En proceso');
-- Animal 5: Princesa, Poodle Toy (hipoa, 3a de Ana — en proceso de adopción)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (5, 5, 4, 'Princesa', 'Completo', TRUE, 'Princesa es una Poodle Toy de 2 años. Hipoalergénica, entrenada y muy cariñosa. Ya tiene un trámite de adopción en proceso. Por favor no enviar solicitudes nuevas.', TRUE, '03100', 24, 'Perro', 4, NULL, NULL, NULL, NULL);

-- Publicacion 20 — usuario 8 (Lucía), EN PROCESO — Pastor Alemán grande
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (20, 8, 'En proceso');
-- Animal 20: Zeus, Pastor Alemán con override_sociable_niños=5 (excepcionalmente bueno con niños)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (20, 20, 8, 'Zeus', 'Completo', TRUE, 'Zeus es un Pastor Alemán de 4 años, entrenado como perro de compañía. Es excepcionalmente gentil con niños, lo cual es atípico para la raza — fue socializado desde cachorro. Tramite en curso. Requiere jardín o espacio amplio.', TRUE, '06800', 48, 'Perro', 3, NULL, NULL, 5, NULL);


-- ============================================================
-- ESCENARIO C: Adopciones COMPLETADAS (estado Adoptado + Contrato)
-- ============================================================

-- Publicacion 6 — usuario 7 (Jorge), ADOPTADO — adopción exitosa Pastor Alemán
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (6, 7, 'Adoptado');
-- Animal 6: Thor, Pastor Alemán (adoptado por Carlos — usuario 3)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (6, 6, 7, 'Thor', 'Completo', TRUE, 'Thor fue adoptado exitosamente. Era un Pastor Alemán de 2 años, leal y trabajador. Ya tiene su hogar para siempre.', TRUE, '04000', 24, 'Perro', 3, NULL, NULL, NULL, NULL);

-- Publicacion 12 — usuario 10 (Valentina), ADOPTADO — adopción exitosa Ragdoll
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (12, 10, 'Adoptado');
-- Animal 12: Mochi, Ragdoll (adoptado por Andrés — usuario 15)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (12, 12, 10, 'Mochi', 'Completo', TRUE, 'Mochi ya fue adoptado. Era un Ragdoll de 18 meses, dócil y silencioso. Perfecto para departamentos. Encontró su hogar ideal.', FALSE, '07100', 18, 'Gato', 19, NULL, NULL, NULL, NULL);


-- ============================================================
-- ESCENARIO D: Solicitudes CANCELADAS y RECHAZADAS (historial)
-- Publicación sigue activa pero con solicitudes de estados anteriores.
-- ============================================================

-- Publicacion 7 — usuario 8 (Lucía), Activa — Siamés (historial de solicitudes fallidas)
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (7, 8, 'Activa');
-- Animal 7: Mía, Siamés (vocal, curiosa)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (7, 7, 8, 'Mía', 'Completo', TRUE, 'Mía es una Siamés de 2.5 años, muy expresiva y curiosa. Le gusta explorar y es muy vocal. Se adapta bien a hogares con adultos. Ha recibido solicitudes previas que no prosperaron. Sigue disponible para adopción.', FALSE, '06800', 30, 'Gato', 14, NULL, NULL, NULL, NULL);


-- ============================================================
-- ESCENARIO E: Publicaciones PAUSADAS y CERRADAS
-- ============================================================

-- Publicacion 8 — usuario 5 (Roberto), PAUSADA
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (8, 5, 'Pausada');
-- Animal 8: Bruno, Golden Retriever (pausado temporalmente por el donante)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (8, 8, 5, 'Bruno', 'Completo', TRUE, 'Bruno es un Golden Retriever de 5 años. Publicación pausada temporalmente mientras el donante resuelve situación personal. Regresará pronto como activa.', TRUE, '06900', 60, 'Perro', 7, NULL, NULL, NULL, NULL);

-- Publicacion 9 — usuario 5 (Roberto), CERRADA
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (9, 5, 'Cerrada');
-- Animal 9: Cleo, Maine Coon (publicación cerrada sin completar adopción)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (9, 9, 5, 'Cleo', 'Parcial', TRUE, 'Cleo, Maine Coon de 3 años. Publicación cerrada — el donante decidió no continuar con el proceso por el momento.', FALSE, '06900', 36, 'Gato', 15, NULL, NULL, NULL, NULL);


-- ============================================================
-- ESCENARIO F: Moderación — Publicaciones SUSPENDIDAS, BORRADAS y EN REVISIÓN
-- ============================================================

-- Publicacion 10 — usuario 11 (Eduardo, baneado), SUSPENDIDA
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (10, 11, 'Suspendida');
-- Animal 10: Kira, Chihuahua (publicación de usuario baneado, suspendida por admin)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (10, 10, 11, 'Kira', 'Ninguno', TRUE, 'Publicación suspendida por violación a los términos de servicio. El usuario fue baneado por reportes múltiples.', FALSE, '09000', 12, 'Perro', 2, NULL, NULL, NULL, NULL);

-- Publicacion 16 — usuario 17 (Alejandro), BORRADA
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (16, 17, 'Borrada');
-- Animal 16: Perla, Bosque de Noruega (publicación borrada por el propio donante)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (16, 16, 17, 'Perla', 'Completo', TRUE, 'Publicación eliminada por el donante. El animal ya fue ubicado con un familiar.', FALSE, '07200', 48, 'Gato', 21, NULL, NULL, NULL, NULL);

-- Publicacion 22 — usuario 14 (Fernanda), EN REVISIÓN (reportada)
INSERT INTO Publicacion (id_publicacion, id_usuario, estado) VALUES (22, 14, 'En revision');
-- Animal 22: Simba, Bengalí (publicación bajo revisión de moderadores)
INSERT INTO Animal (id_animal, id_publicacion, id_usuario, nombre, estado_vacunacion, esterilizado, descripcion, entrenado, codigo_postal, edad, tipo, id_raza, override_energia, override_independencia, override_sociable_niños, override_sociable_mascotas)
VALUES (22, 22, 14, 'Simba', 'Parcial', TRUE, 'Simba es un Bengalí de 2 años. Publicación actualmente en revisión por un reporte de usuario. En espera de resolución del equipo de moderación.', FALSE, '15000', 24, 'Gato', 18, NULL, NULL, NULL, NULL);


-- ============================================================
-- FOTOS DE ANIMALES
-- ============================================================

-- Fotos para animales con publicaciones activas (visibles en mapa y listado)
INSERT INTO Fotos (id_animal, id_publicacion, id_usuario, foto) VALUES
-- Max (Labrador, pub 1, user 2)
(1, 1, 2, 'uploads/max_labrador_01.jpg'),
(1, 1, 2, 'uploads/max_labrador_02.jpg'),
(1, 1, 2, 'uploads/max_labrador_03.jpg'),
-- Luna (Persa, pub 2, user 2)
(2, 2, 2, 'uploads/luna_persa_01.jpg'),
(2, 2, 2, 'uploads/luna_persa_02.jpg'),
-- Rocky (Beagle, pub 3, user 2)
(3, 3, 2, 'uploads/rocky_beagle_01.jpg'),
(3, 3, 2, 'uploads/rocky_beagle_02.jpg'),
(3, 3, 2, 'uploads/rocky_beagle_03.jpg'),
-- Bella (Poodle, pub 4, user 4)
(4, 4, 4, 'uploads/bella_poodle_01.jpg'),
(4, 4, 4, 'uploads/bella_poodle_02.jpg'),
-- Princesa (Poodle En proceso, pub 5, user 4)
(5, 5, 4, 'uploads/princesa_poodle_01.jpg'),
-- Thor (adoptado, pub 6, user 7)
(6, 6, 7, 'uploads/thor_pastor_01.jpg'),
(6, 6, 7, 'uploads/thor_pastor_02.jpg'),
-- Mía (Siamés, pub 7, user 8)
(7, 7, 8, 'uploads/mia_siames_01.jpg'),
(7, 7, 8, 'uploads/mia_siames_02.jpg'),
-- Bruno (pausado, pub 8, user 5)
(8, 8, 5, 'uploads/bruno_golden_01.jpg'),
-- Nala (Border Collie, pub 11, user 10)
(11, 11, 10, 'uploads/nala_bc_01.jpg'),
(11, 11, 10, 'uploads/nala_bc_02.jpg'),
-- Titán (Schnauzer, pub 13, user 13)
(13, 13, 13, 'uploads/titan_schnauzer_01.jpg'),
(13, 13, 13, 'uploads/titan_schnauzer_02.jpg'),
(13, 13, 13, 'uploads/titan_schnauzer_03.jpg'),
-- Isis (Sphynx, pub 14, user 16)
(14, 14, 16, 'uploads/isis_sphynx_01.jpg'),
(14, 14, 16, 'uploads/isis_sphynx_02.jpg'),
-- Rex (Border Collie, pub 15, user 17)
(15, 15, 17, 'uploads/rex_bc_01.jpg'),
-- Lola (Bichón Frisé, pub 18, user 2)
(18, 18, 2, 'uploads/lola_bichon_01.jpg'),
(18, 18, 2, 'uploads/lola_bichon_02.jpg'),
-- Mango (Abisinio, pub 19, user 14)
(19, 19, 14, 'uploads/mango_abisinio_01.jpg'),
-- Zeus (En proceso, pub 20, user 8)
(20, 20, 8, 'uploads/zeus_pastor_01.jpg'),
(20, 20, 8, 'uploads/zeus_pastor_02.jpg'),
-- Pepita (rescatada, pub 21, user 3)
(21, 21, 3, 'uploads/pepita_gata_01.jpg'),
-- Simba (En revision, pub 22, user 14)
(22, 22, 14, 'uploads/simba_bengali_01.jpg'),
-- Coco (Bulldog, pub 17, user 4)
(17, 17, 4, 'uploads/coco_bulldog_01.jpg'),
(17, 17, 4, 'uploads/coco_bulldog_02.jpg');


-- ============================================================
-- PADECIMIENTOS
-- Cubre: animales con condiciones médicas (edge cases para info detallada)
-- ============================================================

INSERT INTO Padecimientos (id_animal, id_publicacion, id_usuario, padecimiento) VALUES
-- Thor (adoptado, pub 6, user 7) — displasia documentada
(6, 6, 7, 'Displasia de cadera leve'),
-- Bruno (pausado, pub 8, user 5) — sobrepeso
(8, 8, 5, 'Sobrepeso'),
-- Kira (suspendida, pub 10, user 11) — sin vacunas y con enfermedad
(10, 10, 11, 'Diabetes tipo 1'),
(10, 10, 11, 'Desnutrición leve'),
-- Simba (en revisión, pub 22, user 14)
(22, 22, 14, 'Dermatitis alérgica'),
-- Zeus (en proceso, pub 20, user 8)
(20, 20, 8, 'Artritis incipiente en cadera derecha'),
-- Pepita (rescatada, pub 21, user 3) — típica de callejeros
(21, 21, 3, 'Anemia leve en recuperación');


-- ============================================================
-- FORMULARIOS DE ADOPCIÓN
-- Cubre: variedad de perfiles para probar el algoritmo de recomendación.
-- Valores enumerados: presupuesto (1=<10k, 3=10-20k, 5=>20k),
--   tiempo_ejercicio/soledad (1=bajo, 3=moderado, 5=alto).
-- ============================================================

-- Formulario 1 — usuario 3 (Carlos): presupuesto alto, activo, con mascotas, sin niños, poca soledad
-- Perfil compatible con: Labrador, Border Collie, Golden Retriever
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (1, 3, 5, 0, '2025-06-15', 1, 3, 1, 0);

-- Formulario 2 — usuario 4 (Ana): presupuesto alto, moderada actividad, con niños y mascotas, poca soledad
-- Perfil compatible con: Labrador, Golden Retriever, Poodle
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (2, 4, 5, 0, '2025-04-10', 1, 3, 1, 1);

-- Formulario 3 — usuario 6 (María): presupuesto bajo, ALERGIAS, con niños, sin mascotas, sedentaria, soledad máxima
-- CASO EXTREMO — solo compatible con razas hipoalergénicas (Poodle, Schnauzer, Bichón, Sphynx, Balinés)
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (3, 6, 1, 1, '2025-05-20', 0, 1, 5, 1);

-- Formulario 4 — usuario 9 (Miguel): presupuesto medio, sin alergias, intenso ejercicio, soledad moderada
-- Perfil compatible con: Border Collie (Rex con override), Pastor Alemán
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (4, 9, 3, 0, '2025-05-01', 0, 5, 3, 0);

-- Formulario 5 — usuario 10 (Valentina): presupuesto medio, muy activa, poca soledad, sin niños ni mascotas
-- Perfil compatible con: Border Collie, Labrador
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (5, 10, 3, 0, '2025-03-30', 0, 5, 1, 0);

-- Formulario 6 — usuario 12 (Isabela): presupuesto alto, niños en casa, con mascotas, actividad moderada, poca soledad
-- Perfil compatible con: Beagle, Labrador, Ragdoll
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (6, 12, 5, 0, '2025-06-01', 1, 3, 1, 1);

-- Formulario 7 — usuario 13 (Diego): presupuesto alto, muy activo, con mascotas, sin niños, soledad moderada
-- Perfil compatible con: Border Collie, Abisinio, Bengalí
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (7, 13, 5, 0, '2025-04-25', 1, 5, 3, 0);

-- Formulario 8 — usuario 15 (Andrés): presupuesto bajo, sedentario, solo, MUCHA soledad (trabaja todo el día)
-- CASO EXTREMO — mejor con razas independientes: Persa, Británico, Bosque de Noruega
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (8, 15, 1, 0, '2025-08-15', 0, 1, 5, 0);

-- Formulario 9 — usuario 16 (Paola): ALERGIAS, sin ejercicio, soledad alta, sin niños, con mascotas
-- Solo compatible con hipoalergénicos: Poodle, Schnauzer, Bichón Frisé, Sphynx, Balinés
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (9, 16, 1, 1, '2025-09-10', 1, 1, 5, 0);

-- Formulario 10 — usuario 18 (Carolina): ALERGIAS, presupuesto medio, con mascotas, moderada
-- Compatible con: Schnauzer, Bichón, Sphynx (hipoalergénicos)
INSERT INTO Formulario (id_formulario, id_usuario, presupuesto, tiene_alergias, fecha_envio, tiene_mascotas, tiempo_ejercicio, tiempo_soledad, tiene_niños)
VALUES (10, 18, 3, 1, '2025-11-20', 1, 1, 3, 0);



-- ============================================================
-- SOLICITUDES DE ADOPCIÓN
-- Cubre: todos los estados (Pendiente, Aprobada, Rechazada, Cancelada),
--        múltiples interesados por publicación, historial de tramites.
-- ============================================================

-- --- Solicitudes para Animal 3 (Rocky, Beagle) — pub 3 (Activa) ---
-- CASO: publicación popular con 3 interesados simultáneos
INSERT INTO Solicitud (id_solicitud, id_usuario, fecha, estado, id_animal, id_publicacion_animal, id_usuario_animal)
VALUES (1, 9,  '2026-05-10', 'Pendiente', 3, 3, 2),  -- Miguel
       (2, 12, '2026-05-11', 'Pendiente', 3, 3, 2),  -- Isabela
       (3, 15, '2026-05-12', 'Pendiente', 3, 3, 2);  -- Andrés

-- --- Solicitudes para Animal 5 (Princesa, Poodle) — pub 5 (En proceso) ---
-- CASO: tramite iniciado, seleccionado y rechazados
INSERT INTO Solicitud (id_solicitud, id_usuario, fecha, estado, id_animal, id_publicacion_animal, id_usuario_animal)
VALUES (4, 6,  '2026-04-15', 'Aprobada',  5, 5, 4),  -- María: SELECCIONADA (tramite iniciado)
       (5, 9,  '2026-04-16', 'Rechazada', 5, 5, 4),  -- Miguel: no seleccionado
       (6, 15, '2026-04-17', 'Rechazada', 5, 5, 4);  -- Andrés: no seleccionado

-- --- Solicitudes para Animal 6 (Thor) — pub 6 (Adoptado) ---
-- CASO: adopción completada, histórico de solicitudes
INSERT INTO Solicitud (id_solicitud, id_usuario, fecha, estado, id_animal, id_publicacion_animal, id_usuario_animal)
VALUES (7, 3,  '2026-02-20', 'Aprobada',  6, 6, 7),  -- Carlos: adoptó a Thor
       (8, 15, '2026-02-21', 'Rechazada', 6, 6, 7);  -- Andrés: no seleccionado

-- --- Solicitudes para Animal 7 (Mía, Siamés) — pub 7 (Activa) ---
-- CASO: solicitud cancelada por el adoptante + solicitud rechazada por el donante
INSERT INTO Solicitud (id_solicitud, id_usuario, fecha, estado, id_animal, id_publicacion_animal, id_usuario_animal)
VALUES (9,  9,  '2026-03-05', 'Cancelada', 7, 7, 8),  -- Miguel: se arrepintió
       (10, 12, '2026-03-08', 'Rechazada', 7, 7, 8);  -- Isabela: rechazada por donante

-- --- Solicitudes para Animal 12 (Mochi, Ragdoll) — pub 12 (Adoptado) ---
-- CASO: histórico de adopción completada (Valentina dona y Andrés adopta)
INSERT INTO Solicitud (id_solicitud, id_usuario, fecha, estado, id_animal, id_publicacion_animal, id_usuario_animal)
VALUES (11, 15, '2026-01-10', 'Aprobada',  12, 12, 10),  -- Andrés: adoptó a Mochi
       (12, 18, '2026-01-11', 'Rechazada', 12, 12, 10);  -- Carolina: no seleccionada

-- --- Solicitudes para Animal 20 (Zeus, Pastor Alemán) — pub 20 (En proceso) ---
-- CASO: tramite iniciado con Diego como seleccionado
INSERT INTO Solicitud (id_solicitud, id_usuario, fecha, estado, id_animal, id_publicacion_animal, id_usuario_animal)
VALUES (13, 13, '2026-05-01', 'Aprobada',  20, 20, 8),  -- Diego: seleccionado
       (14, 6,  '2026-05-02', 'Rechazada', 20, 20, 8);  -- María: rechazada

-- --- Solicitud para Animal 1 (Max, Labrador) — pub 1 (Activa) ---
-- CASO: interés reciente aún sin respuesta
INSERT INTO Solicitud (id_solicitud, id_usuario, fecha, estado, id_animal, id_publicacion_animal, id_usuario_animal)
VALUES (15, 6, '2026-05-30', 'Pendiente', 1, 1, 2);  -- María: pendiente


-- ============================================================
-- CONTRATOS DE ADOPCIÓN
-- Cubre: formalización del acuerdo entre donante y adoptante.
-- chk_donante_consistente: id_usuario_donante = id_usuario_animal
-- ============================================================

-- Contrato 1: Jorge (donante id=7) → Carlos (adoptante id=3), Thor (animal id=6, pub 6, user_animal=7)
INSERT INTO Contrato (id_contrato, fecha, terminos, id_usuario_donante, id_usuario_adoptante, id_animal, id_publicacion_animal, id_usuario_animal)
VALUES (1, '2026-03-01',
'El adoptante Carlos Mendoza se compromete a: 1) Brindar atención veterinaria periódica a Thor. 2) No revender ni ceder al animal a terceros sin notificación previa. 3) Proveer alimentación de calidad y espacio adecuado para una raza de talla grande. El donante Jorge Herrera declara que el animal está esterilizado, vacunado y sin enfermedades diagnosticadas al momento de la entrega, salvo displasia de cadera leve documentada. Ambas partes acuerdan que esta adopción es irrevocable salvo causa de fuerza mayor.',
7, 3, 6, 6, 7);

-- Contrato 2: Valentina (donante id=10) → Andrés (adoptante id=15), Mochi (animal id=12, pub 12, user_animal=10)
INSERT INTO Contrato (id_contrato, fecha, terminos, id_usuario_donante, id_usuario_adoptante, id_animal, id_publicacion_animal, id_usuario_animal)
VALUES (2, '2026-01-20',
'El adoptante Andrés Jiménez se compromete a: 1) Mantener a Mochi exclusivamente en interiores dada su naturaleza Ragdoll. 2) Llevar a revisión veterinaria semestral. 3) No exponerlo a temperaturas extremas. La donante Valentina Castro garantiza que Mochi está sano, esterilizado y con vacunas al corriente. Ambas partes firman en señal de conformidad con las condiciones de la adopción.',
10, 15, 12, 12, 10);


-- ============================================================
-- REPORTES DE PUBLICACIONES
-- Cubre: todos los estados del ciclo de moderación.
-- ============================================================

-- Reporte 1: Carlos reporta la publicación de Simba (pub 22, user 14) — En Revisión
INSERT INTO Reporte (id_reporte, id_usuario, id_publicacion, id_usuario_publicacion, estado, fecha, motivo, id_administrador)
VALUES (1, 3, 22, 14, 'Pendiente', '2026-05-15',
'La descripción del animal parece copiada de otra publicación. Las fotos no coinciden con la descripción de la raza. Sospecho que no es una adopción real.',
1);

-- Reporte 2: María reporta la publicación de Kira (pub 10, user 11) — Atendido (derivó en ban)
INSERT INTO Reporte (id_reporte, id_usuario, id_publicacion, id_usuario_publicacion, estado, fecha, motivo, id_administrador)
VALUES (2, 6, 10, 11, 'Atendido', '2026-04-20',
'El usuario ofrece al animal a cambio de dinero en los mensajes privados, lo cual viola explícitamente los términos de servicio de Adogta. Tengo capturas de pantalla disponibles.',
2);

-- Reporte 3: Miguel reporta Rocky (pub 3, user 2) — Pendiente (sin admin asignado aún)
INSERT INTO Reporte (id_reporte, id_usuario, id_publicacion, id_usuario_publicacion, estado, fecha, motivo, id_administrador)
VALUES (3, 9, 3, 2, 'Pendiente', '2026-05-28',
'Las fotos del animal parecen de stock. Solicité más información al donante y no obtuvo respuesta en 2 semanas.',
NULL);

-- Reporte 4: Andrés reporta pub 10 (user 11) — Desestimado (otro reporte al mismo, ya resuelto)
INSERT INTO Reporte (id_reporte, id_usuario, id_publicacion, id_usuario_publicacion, estado, fecha, motivo, id_administrador)
VALUES (4, 15, 10, 11, 'Desestimado', '2026-04-25',
'Creo que el donante no tiene realmente al animal. No responde mensajes.',
3);


-- ============================================================
-- BANS
-- Cubre: usuario suspendido por el administrador tras reporte atendido.
-- ============================================================

-- Ban de Eduardo Flores (id=11) por admin Marco Rubio (id=1)
INSERT INTO Ban (id_ban, id_usuario, por, fecha, motivo, id_administrador)
VALUES (1, 11, 'Marco Rubio (Administrador)', '2026-04-22',
'El usuario Eduardo Flores fue suspendido permanentemente por comercializar animales bajo la apariencia de adopciones. Se recibieron dos reportes independientes con evidencia de cobros. La publicación id=10 fue suspendida. Se notificó al usuario por correo electrónico.',
1);


-- ============================================================
-- RESETEO DE SECUENCIAS
-- Necesario porque los IDs fueron insertados de forma explícita.
-- ============================================================

SELECT setval('usuario_id_usuario_seq',       18, true);
SELECT setval('publicacion_id_publicacion_seq', 22, true);
SELECT setval('animal_id_animal_seq',           22, true);
SELECT setval('solicitud_id_solicitud_seq',     15, true);
SELECT setval('contrato_id_contrato_seq',        2, true);
SELECT setval('reporte_id_reporte_seq',          4, true);
SELECT setval('formulario_id_formulario_seq',   10, true);
SELECT setval('ban_id_ban_seq',                  1, true);
SELECT setval('administrador_id_administrador_seq', 6, true);
SELECT setval('raza_id_raza_seq',               22, true);
