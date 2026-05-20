package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Entidad JPA que mapea la tabla [reporte] en la base de datos.
 *
 * Representa una denuncia realizada por un usuario sobre una publicación.
 */
@Entity
@Table(name = "reporte")
data class ReporteEntity(

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

    /** Relación de solo lectura hacia el usuario que denuncia. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    val usuario: UsuarioEntity? = null,

    /** Relación de solo lectura hacia la publicación denunciada. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
        JoinColumn(name = "id_publicacion", referencedColumnName = "id_publicacion", insertable = false, updatable = false),
        JoinColumn(name = "id_usuario_publicacion", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    )
    val publicacion: PublicacionEntity? = null,

    /** Estado del reporte. */
    @Column(length = 50)
    var estado: String = "Pendiente",

    /** Fecha en que se registró el reporte. */
    @Column(nullable = false)
    var fecha: LocalDate = LocalDate.now(),

    /** Motivo de la denuncia. */
    @Column(columnDefinition = "TEXT")
    var motivo: String = ""
)