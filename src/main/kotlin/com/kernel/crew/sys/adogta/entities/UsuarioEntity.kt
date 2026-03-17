package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Entidad JPA que mapea directamente la tabla [Usuario] en la base de datos.
 *
 * Esta clase pertenece a la capa de persistencia y no debe usarse
 * directamente en la lógica de negocio ni en las respuestas al cliente.
 * Para eso existen [com.kernel.crew.sys.adogta.domain.Usuario] y
 * [com.kernel.crew.sys.adogta.dto.response.UsuarioResponse].
 */
@Entity
@Table(name = "usuario")
data class UsuarioEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    val id: Long = 0,

    @Column(name = "nombres", nullable = false, length = 100)
    val nombres: String = "",

    @Column(name = "apellido_paterno", nullable = false, length = 100)
    val apellidoPaterno: String = "",

    @Column(name = "apellido_materno", nullable = true, length = 100)
    val apellidoMaterno: String? = null,

    @Column(name = "email", nullable = false, unique = true, length = 255)
    val email: String = "",

    @Column(name = "google_id", nullable = true, unique = true, length = 255)
    val googleId: String? = null,

    @Column(name = "contrasena", nullable = true, length = 255)
    val contrasena: String? = null,

    @Column(name = "acepta_terminos", nullable = false)
    val aceptaTerminos: Boolean = false,

    @Column(name = "fecha_acepta_terminos", nullable = true)
    val fechaAceptaTerminos: LocalDate? = null,

    @Column(name = "codigo_postal", nullable = false, length = 5)
    val codigoPostal: String = "",

    @Column(name = "telefono", nullable = true, length = 15)
    val telefono: String? = null,

    @Column(name = "proveedor_autenticacion", nullable = false, length = 50)
    val proveedorAutenticacion: String = "",

    @Column(name = "token_sesion", nullable = true, length = 255)
    val tokenSesion: String? = null,

    @Column(name = "fecha_expiracion_sesion", nullable = true)
    val fechaExpiracionSesion: LocalDateTime? = null,

    @Column(name = "token_recuperacion_contrasena", nullable = true, length = 255)
    val tokenRecuperacionContrasena: String? = null,

    @Column(name = "fecha_expiracion_token_recuperacion", nullable = true)
    val fechaExpiracionTokenRecuperacion: LocalDateTime? = null,

    @Column(name = "es_adoptante", nullable = false)
    val esAdoptante: Boolean = false,

    @Column(name = "es_donante", nullable = false)
    val esDonante: Boolean = false
)