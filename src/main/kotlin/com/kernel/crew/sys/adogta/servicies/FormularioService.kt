package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.FormularioRequest
import com.kernel.crew.sys.adogta.entities.FormularioEntity
import com.kernel.crew.sys.adogta.repositories.FormularioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

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
            usuario = request.usuario,
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
