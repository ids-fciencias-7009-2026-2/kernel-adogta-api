package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Entidad JPA que mapea la tabla [administrador] en la base de datos.
 *
 * Representa a un usuario con privilegios de moderación (revisión de reportes y baneos).
 */
@Entity
@Table(name = "administrador")
data class AdministradorEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_administrador")
    val idAdministrador: Long = 0,

    /** Correo electrónico del administrador. */
    @Column(length = 150)
    var email: String = "",

    /** Contraseña del administrador. */
    @Column(length = 255)
    var contrasena: String = "",

    /** Nombre(s) del administrador. */
    @Column(length = 100)
    var nombres: String = "",

    /** Apellido paterno del administrador. */
    @Column(name = "apellido_paterno", length = 100)
    var apellidoPaterno: String = "",

    /** Apellido materno del administrador. */
    @Column(name = "apellido_materno", length = 100)
    var apellidoMaterno: String? = null,

    /** Token de sesión activo para administradores. */
    @Column(name = "token_sesion", length = 255)
    var tokenSesion: String? = null,

    /** Fecha y hora de expiración del token de sesión. */
    @Column(name = "fecha_expiracion_sesion")
    var fechaExpiracionSesion: LocalDateTime? = null
)