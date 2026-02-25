package com.kernel.crew.sys.adogta.dto.response

/**
 * DTO que representa la respuesta con información del usuario.
 *
 * Excluye campos sensibles como password, googleId, tokens
 * y datos internos de auditoría.
 */
data class UsuarioResponse(

    /**
     * Identificador único del usuario.
     * */
    val id: Long,

    /**
     * Nombre(s) del usuario.
     * */
    val nombre: String,

    /**
     * Primer apellido del usuario.
     * */
    val apellidoPaterno: String,

    /**
     * Segundo apellido del usuario.
     * */
    val apellidoMaterno: String,

    /**
     * Correo electrónico del usuario.
     * */
    val email: String,

    /**
     * Código postal del usuario.
     * */
    val codigoPostal: String,

    /**
     * Número telefónico del usuario. Opcional.
     * */
    val telefono: String?,

    /**
     * Proveedor de autenticación: "local" o "google".
     * */
    val authProvider: String,

    /**
     * Rol del usuario en el sistema: "usuario" o "admin".
     * */
    val rol: String,

    /**
     * Indica si el correo electrónico fue verificado.
     * */
    val emailVerificado: Boolean,

    /**
     * Indica si el usuario está baneado del sistema.
     * */
    val isBaned: Boolean,

    /**
     * Puntuación de reputación del usuario.
     * */
    val reputation: Int,

    /**
     * Indica si el usuario aceptó los términos y condiciones.
     * */
    val aceptaTerminos: Boolean,

    /**
     * Fecha de registro en el sistema.
     * */
    val fechaRegistro: String,

    /**
     * Fecha del último acceso.
     * */
    val ultimoAcceso: String
)