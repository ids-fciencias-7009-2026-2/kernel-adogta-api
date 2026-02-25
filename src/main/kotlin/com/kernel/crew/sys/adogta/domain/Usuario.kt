package com.kernel.crew.sys.adogta.domain

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
     * Correo electrónico único del usuario.
     * */
    val email: String,

    /**
     * Código postal del usuario.
     * */
    val codigoPostal: String,

    /**
     *  Número telefónico del usuario. Opcional.
     *  */
    val telefono: String?,

    /**
     * Contraseña hasheada. Null si el proveedor de autenticación es Google.
     * */
    val password: String?,

    /**
     * Identificador de cuenta Google. Null si el proveedor es local.
     * */
    val googleId: String?,

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
     * Motivo del baneo. Null si el usuario no está baneado.
     * */
    val banMotive: String?,

    /**
     * Fecha del baneo. Null si el usuario no está baneado.
     * */
    val banDate: String?,

    /**
     * ID del administrador que aplicó el baneo. Null si no aplica.
     * */
    val bannedBy: Long?,

    /**
     * Puntuación de reputación del usuario. Default 0.
     * */
    val reputation: Int,

    /**
     * Indica si el usuario aceptó los términos y condiciones.
     * */
    val aceptaTerminos: Boolean,

    /**
     * Fecha en que se aceptaron los términos. Null si no aplica.
     * */
    val fechaAceptaTerminos: String?,

    /**
     * Fecha de registro en el sistema.
     * */
    val fechaRegistro: String,

    /**
     * Fecha del último acceso. Se actualiza en cada login.
     * */
    val ultimoAcceso: String,

    /**
     * Fecha de la última modificación del perfil.
     * */
    val fechaUpdate: String
)