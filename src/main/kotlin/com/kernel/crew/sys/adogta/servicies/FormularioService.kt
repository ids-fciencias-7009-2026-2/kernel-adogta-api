package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.FormularioRequest
import com.kernel.crew.sys.adogta.dto.response.FormularioResponse
import com.kernel.crew.sys.adogta.entities.FormularioEntity
import com.kernel.crew.sys.adogta.extensions.toEntity
import com.kernel.crew.sys.adogta.extensions.toResponse
import com.kernel.crew.sys.adogta.repositories.FormularioRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

/**
 * Servicio que contiene la lógica de negocio relacionada con la entidad Formulario.
 *
 * Se encarga de registrar nuevos formularios, obtener el historial de formularios de un usuario,
 */
@Service
class FormularioService{
    @Autowired
    lateinit var formularioRepository: FormularioRepository

    fun guardarFormulario(request: FormularioRequest): FormularioEntity {
        val nuevoFormulario = FormularioEntity(
            presupuesto = request.presupuesto,
            tieneAlergias = request.tieneAlergias,
            fechaEnvio = LocalDate.parse(request.fechaEnvio),
            tieneMascotas = request.tieneMascotas,
            tiempoEjercicio = request.tiempoEjercicio,
            tiempoSoledad = request.tiempoSoledad,
            tieneNiños = request.tieneNiños
        )
        
        return formularioRepository.save(nuevoFormulario)
    }

    fun obtenerFormulariosPorUsuario(idUsuario: Long): List<FormularioEntity> {
        return formularioRepository.findAllByUsuarioId(idUsuario)
    }

}
