package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Entidad JPA que representa la tabla Solicitud del DDL.
 *
 * La PK es compuesta (id_solicitud, id_usuario).
 * id_solicitud es generado por la secuencia solicitud_id_solicitud_seq de PostgreSQL.
 * id_usuario corresponde al adoptante que envió la solicitud.
 */
@Entity
@Table(name = "solicitud")
@IdClass(SolicitudId::class)
class SolicitudEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "solicitud_seq")
    @SequenceGenerator(
        name = "solicitud_seq",
        sequenceName = "solicitud_id_solicitud_seq",
        allocationSize = 1
    )
    @Column(name = "id_solicitud")
    val idSolicitud: Int = 0,

    @Id
    @Column(name = "id_usuario")
    val idUsuario: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    val usuario: UsuarioEntity,

    @Column(name = "fecha")
    var fecha: LocalDate = LocalDate.now(),

    @Column(length = 50)
    var estado: String = "Pendiente",

    @Column(name = "id_animal")
    var idAnimal: Int,

    @Column(name = "id_publicacion_animal")
    var idPublicacionAnimal: Int,

    @Column(name = "id_usuario_animal")
    var idUsuarioAnimal: Int
)