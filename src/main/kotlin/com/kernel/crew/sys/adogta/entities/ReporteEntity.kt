package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Entidad JPA que mapea directamente la tabla [Reporte] en la base de datos.
 */
@Entity
@Table(name = "reporte")
data class ReporteEntity(

    /** ID del reporte. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    val idReporte: Long = 0,

    /** ID del usuario que realizó la denuncia. */
    @Column(name = "id_usuario")
    var idUsuario: Long = 0,

    /** ID de la publicación denunciada. */
    @Column(name = "id_publicacion")
    var idPublicacion: Int = 0,

    /** ID del dueño de la publicación denunciada. */
    @Column(name = "id_usuario_publicacion")
    var idUsuarioPublicacion: Int = 0,

    /** Relación de solo lectura hacia el denunciante. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    val usuario: UsuarioEntity? = null,

    /** Estado del reporte, por defecto se marca como pendiente. */
    @Column(length = 50)
    var estado: String = "Pendiente",

    /** Fecha en la que se realizó el reporte. */
    @Column(nullable = false)
    var fecha: LocalDate = LocalDate.now(),

    /** Motivo del reporte. */
    @Column(columnDefinition = "TEXT")
    var motivo: String = "",

    /** ID del administrador que resolvió el reporte. NULL mientras está pendiente. */
    @Column(name = "id_administrador")
    var idAdministrador: Long? = null
)