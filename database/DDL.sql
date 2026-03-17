-- ============================================
-- TABLA: Usuario
-- ============================================
-- Almacena la información de todos los usuarios
-- registrados en la plataforma. Un usuario puede
-- tener el rol de adoptante, donante, o ambos.
-- La autenticación puede ser local
-- o mediante Google OAuth.
-- ============================================
CREATE TABLE Usuario (

    -- Identificador único del usuario, generado automáticamente por la base de datos.
    id_usuario SERIAL PRIMARY KEY,

    -- Nombre(s) del usuario. No puede estar vacío.
    nombres VARCHAR(100) NOT NULL,

    -- Primer apellido del usuario, es obligatorio.
    apellido_paterno VARCHAR(100) NOT NULL,

    -- Segundo apellido del usuario.
    apellido_materno VARCHAR(100) NULL,

    -- Correo electrónico del usuario. Se usa como identificador
    -- único para el login y como medio de contacto principal.
    email VARCHAR(255) UNIQUE NOT NULL,

    -- Identificador de cuenta de Google. Solo se llena cuando
    -- el usuario se registra o autentica mediante Google OAuth.
    google_id VARCHAR(255) UNIQUE NULL,

    -- Contraseña del usuario almacenada como hash SHA-256.
    -- NUNCA se guarda en texto plano.
    -- Es NULL cuando el usuario se autentica mediante Google,
    -- ya que en ese caso no se maneja contraseña propia.
    contrasena VARCHAR(255) NULL,

    -- Indica si el usuario aceptó los términos y condiciones
    -- al momento del registro.
    -- No puede registrarse un usuario sin aceptarlos.
    acepta_terminos BOOLEAN NOT NULL,

    -- Fecha en que el usuario aceptó los términos y condiciones.
    -- Se registra en el momento del registro.
    -- Es NULL si el usuario aún no ha completado su perfil.
    fecha_acepta_terminos DATE NULL,

    -- Código postal del lugar donde se encuentra el usuario.
    -- Se usa como referencia de ubicación aproximada en el mapa
    -- sin revelar la dirección exacta. Exactamente 5 dígitos.
    codigo_postal VARCHAR(5) NOT NULL,

    -- Número de teléfono del usuario. Opcional.
    -- Puede incluir código de país con '+'.
    telefono VARCHAR(15) NULL,

    -- Indica el proveedor de autenticación usado por el usuario.
    -- Valores esperados: 'local' o 'google'.
    proveedor_autenticacion VARCHAR(50) NOT NULL,

    -- Token UUID generado al iniciar sesión. Se usa en el
    -- header 'Authorization' para autenticar peticiones.
    token_sesion VARCHAR(255) NULL,

    -- Fecha y hora de expiración del token de sesión.
    fecha_expiracion_sesion TIMESTAMP NULL,

    -- Token generado cuando el usuario solicita recuperar
    -- su contraseña. Se envía por correo y tiene vigencia limitada.
    token_recuperacion_contrasena VARCHAR(255) NULL,

    -- Fecha y hora de expiración del token de recuperación
    -- de contraseña. Pasada esta fecha, el token es inválido
    -- y el usuario debe solicitar uno nuevo.
    fecha_expiracion_token_recuperacion TIMESTAMP NULL,

    -- Indica si el usuario tiene el rol de adoptante.
    -- Los adoptantes pueden buscar animales y expresar interés
    -- en adoptar mediante el botón 'Me interesa'.
    es_adoptante BOOLEAN NOT NULL DEFAULT FALSE,

    -- Indica si el usuario tiene el rol de donante.
    -- Los donantes pueden publicar animales en adopción
    -- y reciben correos cuando alguien muestra interés.
    es_donante BOOLEAN NOT NULL DEFAULT FALSE,


    -- ============================================
    -- CONSTRAINTS DE VALIDACIÓN
    -- ============================================

    -- Un usuario debe tener al menos un rol activo.
    -- No tiene sentido registrar un usuario que no sea
    -- ni adoptante ni donante.
    CONSTRAINT chk_rol_usuario
        CHECK (es_adoptante = TRUE OR es_donante = TRUE),

    -- El campo nombres no puede ser una cadena vacía o de solo espacios.
    -- Complementa el NOT NULL para evitar registros inválidos.
    CONSTRAINT chk_nombres_vacios
        CHECK (TRIM(nombres) <> ''),

    -- El apellido paterno no puede ser una cadena vacía o de solo espacios.
    CONSTRAINT chk_paterno_vacio
        CHECK (TRIM(apellido_paterno) <> ''),

    -- Validación básica del formato de email.
    -- Verifica que tenga el patrón mínimo: algo@algo.algo
    CONSTRAINT chk_email_formato
        CHECK (email LIKE '%_@__%.__%'),

    -- El código postal debe ser exactamente 5 dígitos numéricos.
    CONSTRAINT chk_cp_formato
        CHECK (codigo_postal ~ '^[0-9]{5}$'),

    -- El teléfono, si se proporciona, solo puede contener
    -- dígitos y opcionalmente un '+' al inicio
    -- para el código de país.
    CONSTRAINT chk_telefono_formato
        CHECK (telefono IS NULL OR telefono ~ '^\+?[0-9\s]+$'),

    -- La contraseña hasheada, si existe, debe tener al menos
    -- 8 caracteres (corresponde al hash de una contraseña
    -- de longitud mínima aceptable).
    -- Es NULL si el proveedor es Google.
    CONSTRAINT chk_contrasena_largo
        CHECK (contrasena IS NULL OR length(contrasena) >= 8)

);