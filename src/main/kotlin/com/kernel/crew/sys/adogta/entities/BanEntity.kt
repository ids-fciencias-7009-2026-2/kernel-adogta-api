package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Entidad JPA que mapea la tabla [ban] en la base de datos.
 *
 * Representa una suspensión aplicada a un usuario por un administrador.
 */
@Entity
@Table(name = "ban")
data class BanEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ban")
    val idBan: Long = 0,

    /** Usuario que recibió la suspensión. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    val usuario: UsuarioEntity,

    /** Nombre del administrador que aplicó el ban. */
    @Column(length = 255)
    var por: String = "",

    /** Fecha en que se aplicó el ban. */
    @Column(nullable = false)
    var fecha: LocalDate = LocalDate.now(),

    /** Motivo de la suspensión. */
    @Column(columnDefinition = "TEXT")
    var motivo: String = ""
)