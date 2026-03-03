package com.kernel.crew.sys.adogta.dto.response

import com.kernel.crew.sys.adogta.dto.request.RegisterRequest

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
    val nombres: String,

    /**
     * Primer apellido del usuario.
     * */
    val apellidoPaterno: String,

    /**
     * Segundo apellido del usuario.
     * */
    val apellidoMaterno: String?,

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
    val proveedorAutenticacion: String,


    /**
     * Indica si el usuario aceptó los términos y condiciones.
     * */
    val aceptaTerminos: Boolean,
)