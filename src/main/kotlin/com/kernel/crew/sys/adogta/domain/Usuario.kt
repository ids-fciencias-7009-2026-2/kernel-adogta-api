package com.kernel.crew.sys.adogta.domain

import com.kernel.crew.sys.adogta.dto.request.RegisterRequest

/**
 * Modelo de dominio que representa a un Usuario dentro del sistema Adogta.
 *
 * Contiene la representación interna de la entidad de negocio,
 * independiente de la capa de persistencia y de la capa de transporte.
 */
data class Usuario(
    /**
     * * Identificador único del usuario.
     * */
    val id: Long,

    /**
     * Nombre(s) del usuario.
     * */
    val nombres: String,

    /**
     * Primer apellido del usuario.
     * */
    val apellidoPaterno: String,

    /**
     * Segundo apellido del usuario. Opcional.
     * */
    val apellidoMaterno: String?,

    /**
     * Correo electrónico único del usuario.
     * */
    val email: String,

    /**
     * Código postal del usuario. Exactamente 5 dígitos.
     * */
    val codigoPostal: String,

    /**
     * Número telefónico del usuario.
     * */
    val telefono: String?,

    /**
     * Identificador de cuenta Google. Null si el proveedor es local.
     * */
    val googleId: String?,

    /**
     * Contraseña hasheada. Null si el proveedor de autenticación es Google.
     * */
    val contrasena: String?,

    /**
     * Indica si el usuario aceptó los términos y condiciones.
     * */
    val aceptaTerminos: Boolean,

    /**
     * Fecha en que se aceptaron los términos.
     * */
    val fechaAceptaTerminos: String?,

    /**
     * Proveedor de autenticación: "local" o "google".
     * */
    val proveedorAutenticacion: String,

    /**
     * Token de sesión activo del usuario.
     * */
    val tokenSesion: String?,

    /**
     * Fecha de expiración del token de sesión.
     * */
    val fechaExpiracionSesion: String?,

    /**
     * Token para recuperación de contraseña.
     * */
    val tokenRecuperacionContrasena: String?,

    /**
     * Fecha de expiración del token de recuperación.
     * */
    val fechaExpiracionTokenRecuperacion: String?,

    /**
     * Indica si el usuario tiene perfil de adoptante.
     * */
    val esAdoptante: Boolean,

    /**
     * Indica si el usuario tiene perfil de donante.
     * */
    val esDonante: Boolean
)