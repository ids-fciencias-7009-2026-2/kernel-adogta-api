package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "solicitud")
class SolicitudEntity(

    @EmbeddedId
    val id: SolicitudId? = null,

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
