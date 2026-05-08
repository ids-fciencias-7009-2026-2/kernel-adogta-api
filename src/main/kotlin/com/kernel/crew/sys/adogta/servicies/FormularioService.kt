package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.FormularioRequest
import com.kernel.crew.sys.adogta.entities.FormularioEntity
import com.kernel.crew.sys.adogta.entities.UsuarioEntity
import com.kernel.crew.sys.adogta.repositories.FormularioRepository
import com.kernel.crew.sys.adogta.repositories.UsuarioRepository
import com.kernel.crew.sys.adogta.servicies.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import com.kernel.crew.sys.adogta.enums.*

/**
 * Servicio que contiene la lógica de negocio relacionada con la entidad Formulario.
 *
 * Se encarga de registrar nuevos formularios, obtener el historial de formularios de un usuario,
 */
@Service
class FormularioService{
    @Autowired
    lateinit var formularioRepository: FormularioRepository

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    open lateinit var usuarioService: UsuarioService

    fun guardarFormulario(request: FormularioRequest, token: String): FormularioEntity {
        val usuarioEncontrado = usuarioService.getAsEntity(token)
        val nuevoFormulario = FormularioEntity(
            usuario = usuarioEncontrado,
            presupuesto = request.presupuesto.valor,
            tieneAlergias = request.tieneAlergias.valor,
            fechaEnvio = LocalDate.parse(request.fechaEnvio),
            tieneMascotas = request.tieneMascotas.valor,
            tiempoEjercicio = request.tiempoEjercicio.valor,
            tiempoSoledad = request.tiempoSoledad.valor,
            tieneNiños = request.tieneNiños.valor
        )

        usuarioRepository.updateCuestionarioStatus(token, true)
        
        return formularioRepository.save(nuevoFormulario)
    }

    fun obtenerFormulariosPorUsuario(idUsuario: Long): List<FormularioEntity> {
        return formularioRepository.findAllByUsuarioId(idUsuario)
    }

    fun obtenerFechaEnvioFormulario(token: String): LocalDate? {
        val usuarioEncontrado = usuarioService.getAsEntity(token) ?: throw NoSuchElementException("No se encontró un usuario con ese token")
        return formularioRepository.getFechaEnvioFormulario(usuarioEncontrado.id)
    }

}
