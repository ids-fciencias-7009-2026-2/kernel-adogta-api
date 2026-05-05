package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

/**
 * Entidad JPA que mapea directamente la tabla [Formulario] en la base de datos.
 *
 * Esta clase pertenece a la capa de persistencia y no debe usarse
 * directamente en la lógica de negocio ni en las respuestas al cliente.
 * Para eso existe [com.kernel.crew.sys.adogta.domain.Formulario].
 */
@Entity
@Table(name = "formulario")
data class FormularioEntity(

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_formulario")
	val id: Long = 0,

	@ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    val usuario: UsuarioEntity,

	@Column(name = "presupuesto", nullable = false)
	val presupuesto: Int = 0,

	@Column(name = "tiene_alergias", nullable = false)
	val tieneAlergias: Int = 0,

	@Column(name = "fecha_envio", nullable = false)
	val fechaEnvio: LocalDate = LocalDate.MIN,

	@Column(name = "tiene_mascotas", nullable = false)
	val tieneMascotas: Int = 0,

	@Column(name = "tiempo_ejercicio", nullable = false)
	val tiempoEjercicio: Int = 0,

	@Column(name = "tiempo_soledad", nullable = false)
	val tiempoSoledad: Int = 0,

	@Column(name = "tiene_niños", nullable = false)
	val tieneNiños: Int = 0
)
