package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*
import java.time.LocalDate

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

    /** Relación de solo lectura hacia el denunciante. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    val usuario: UsuarioEntity? = null,

    @Column(length = 50)
    var estado: String = "Pendiente",

    @Column(nullable = false)
    var fecha: LocalDate = LocalDate.now(),

    @Column(columnDefinition = "TEXT")
    var motivo: String = ""
)