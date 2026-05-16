package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.FormularioRequest
import com.kernel.crew.sys.adogta.entities.FormularioEntity
import com.kernel.crew.sys.adogta.entities.UsuarioEntity
import com.kernel.crew.sys.adogta.repositories.FormularioRepository
import com.kernel.crew.sys.adogta.repositories.UsuarioRepository
import com.kernel.crew.sys.adogta.servicies.UsuarioService
import com.kernel.crew.sys.adogta.dto.response.FormularioResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import com.kernel.crew.sys.adogta.enums.*
import com.kernel.crew.sys.adogta.dto.response.FormularioStringsResponse

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

    /**
     * Obtiene el formulario contestado por el usuario.
     * @return [FormularioResponse] eoc null.
     */
    fun obtenerUltimoFormularioResponse(token: String): FormularioResponse? {
        val usuario = usuarioService.getAsEntity(token)
            ?: throw NoSuchElementException("Usuario no encontrado")
        val formularios = formularioRepository.findAllByUsuarioId(usuario.id)
        val ultimo = formularios.maxByOrNull { it.fechaEnvio } ?: return null
        return FormularioResponse(
            id = ultimo.id,
            presupuesto = ultimo.presupuesto,
            tieneAlergias = ultimo.tieneAlergias,
            fechaEnvio = ultimo.fechaEnvio.toString(),
            tieneMascotas = ultimo.tieneMascotas,
            tiempoEjercicio = ultimo.tiempoEjercicio,
            tiempoSoledad = ultimo.tiempoSoledad,
            tieneNiños = ultimo.tieneNiños
        )
    }

    /**
     * Obtiene el último formulario contestado por el usuario autenticado,
     * devolviendo las etiquetas de cada respuesta en lugar de los valores numéricos.
     *
     * @param token Token de sesión del usuario.
     * @return [FormularioStringsResponse] o null si no ha contestado ninguno.
     */
    fun obtenerUltimoFormularioStrings(token: String): FormularioStringsResponse? {
        val usuario = usuarioService.getAsEntity(token)
            ?: throw NoSuchElementException("Usuario no encontrado")
        val formularios = formularioRepository.findAllByUsuarioId(usuario.id)
        val ultimo = formularios.maxByOrNull { it.fechaEnvio } ?: return null

        return FormularioStringsResponse(
            id = ultimo.id,
            presupuesto = Presupuesto.entries.firstOrNull { it.valor == ultimo.presupuesto }?.name ?: "—",
            tieneAlergias = TieneAlergias.entries.firstOrNull { it.valor == ultimo.tieneAlergias }?.name ?: "—",
            fechaEnvio = ultimo.fechaEnvio.toString(),
            tieneMascotas = TieneMascotas.entries.firstOrNull { it.valor == ultimo.tieneMascotas }?.name ?: "—",
            tiempoEjercicio = TiempoEjercicio.entries.firstOrNull { it.valor == ultimo.tiempoEjercicio }?.name ?: "—",
            tiempoSoledad = TiempoSoledad.entries.firstOrNull { it.valor == ultimo.tiempoSoledad }?.name ?: "—",
            tieneNiños = TieneNiños.entries.firstOrNull { it.valor == ultimo.tieneNiños }?.name ?: "—"
        )
    }

}
