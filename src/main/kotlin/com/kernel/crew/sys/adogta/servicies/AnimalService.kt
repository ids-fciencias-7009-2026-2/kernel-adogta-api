package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.AnimalRequest
import com.kernel.crew.sys.adogta.dto.response.AnimalListItemResponse
import com.kernel.crew.sys.adogta.dto.response.AnimalResponse
import com.kernel.crew.sys.adogta.entities.AnimalEntity
import com.kernel.crew.sys.adogta.entities.PublicacionEntity
import com.kernel.crew.sys.adogta.repositories.AnimalRepository
import com.kernel.crew.sys.adogta.repositories.PublicacionRepository
import com.kernel.crew.sys.adogta.repositories.RazaRepository
import com.kernel.crew.sys.adogta.repositories.UsuarioRepository
import com.kernel.crew.sys.adogta.dto.request.AnimalUpdateRequest
import com.kernel.crew.sys.adogta.dto.response.AnimalDetailResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.kernel.crew.sys.adogta.repositories.FormularioRepository
import com.kernel.crew.sys.adogta.servicies.UsuarioService

@Service
class AnimalService(
    private val animalRepository: AnimalRepository,
    private val publicacionRepository: PublicacionRepository,
    private val razaRepository: RazaRepository,
    private val usuarioRepository: UsuarioRepository,
    private val usuarioService: UsuarioService,
    private val formularioRepository: FormularioRepository
) {
    private val logger = LoggerFactory.getLogger(AnimalService::class.java)

    /**
     * Publica un nuevo animal en adopción asociado al usuario autenticado.
     *
     * @param token Token de sesión del usuario autenticado.
     * @param request Datos del animal a publicar.
     * @return [AnimalResponse] del animal recién publicado, o null si la sesión es inválida.
     */
    @Transactional
    fun publicarAnimal(token: String, request: AnimalRequest): AnimalResponse? {
        logger.info("Publicando animal: ${request.nombre} (${request.tipo}, raza ${request.idRaza})")

        val usuarioAutenticado = usuarioService.getMe(token)
            ?: run {
                logger.warn("Sesión inválida al publicar animal")
                return null
            }

        val usuario = usuarioRepository.findById(usuarioAutenticado.id)
            .orElseThrow { RuntimeException("Usuario no encontrado con id: ${usuarioAutenticado.id}") }

        val raza = razaRepository.findById(request.idRaza)
            .orElseThrow { RuntimeException("Raza no encontrada con id: ${request.idRaza}") }

        if (request.esterilizado != true)
            throw IllegalArgumentException("Solo se aceptan animales esterilizados.")

        val publicacionGuardada = publicacionRepository.save(
            PublicacionEntity(
                idUsuario = usuario.id!!.toInt(),
                usuario = usuario,
                estado = "Activa"
            )
        )

        val animalGuardado = animalRepository.save(
            AnimalEntity(
                idPublicacion = publicacionGuardada.idPublicacion,
                idUsuario = usuario.id!!.toInt(),
                publicacion = publicacionGuardada,
                usuario = usuario,
                nombre = request.nombre.trim(),
                estadoVacunacion = request.estadoVacunacion,
                esterilizado = request.esterilizado,
                descripcion = request.descripcion.trim(),
                entrenado = request.entrenado,
                codigoPostal = request.codigoPostal,
                edad = request.edad,
                tipo = request.tipo,
                raza = raza,
                overrideEnergia = request.overrideEnergia,
                overrideIndependencia = request.overrideIndependencia,
                overrideSociableNiños = request.overrideSociableNiños,
                overrideSociableMascotas = request.overrideSociableMascotas,
                padecimientos = request.padecimientos.map { it.trim() }.filter { it.isNotEmpty() }.toMutableSet(),
                fotos = request.fotos.map { it.trim() }.filter { it.isNotEmpty() }.toMutableSet()
            )
        )

        logger.info("Animal publicado: id=${animalGuardado.idAnimal}, publicacion=${publicacionGuardada.idPublicacion}")

        return AnimalResponse(
            idAnimal = animalGuardado.idAnimal,
            idPublicacion = publicacionGuardada.idPublicacion,
            nombre = animalGuardado.nombre
        )
    }

    /**
     * Lista las publicaciones del usuario autenticado.
     * @param token Token de sesión del usuario.
     * @return Lista de [AnimalResponse] con idAnimal, idPublicacion y nombre.
     */
    @Transactional(readOnly = true)
    fun listarMisPublicaciones(token: String): List<AnimalListItemResponse> {
        logger.info("listando publicaciones par token: $token")
        val usuario = usuarioService.getMe(token) ?: throw Exception("Token inválido")
        val publicaciones = publicacionRepository.findByIdUsuario(usuario.id.toInt())
        val misPublicacionesResponse = mutableListOf<AnimalListItemResponse>()

        for (publicacion in publicaciones) {
            val animalesPublicados = animalRepository.findByIdPublicacion(publicacion.idPublicacion)
            for (animalPublicado in animalesPublicados) {
                misPublicacionesResponse.add(AnimalListItemResponse.from(animalPublicado))
            }
        }
        return misPublicacionesResponse
    }

    /**
     * Retorna la lista de publicaciones activas con sus animales.
     *
     * @return Lista de [AnimalListItemResponse] con los animales en adopción.
     */
    @Transactional(readOnly = true)
    fun listarPublicaciones(): List<AnimalListItemResponse> {
        logger.info("Listando publicaciones de animales activas")
        return animalRepository.findAll()
            .filter { it.publicacion.estado == "Activa" }
            .map(AnimalListItemResponse::from)
    }

    /**
     * Obtiene el detalle público de un animal por su ID.
     * Las publicaciones con estado "Borrada" se tratan como inexistentes.
     *
     * @param idAnimal ID del animal a consultar.
     * @return [AnimalListItemResponse] del animal, o null si no existe o fue borrado.
     */
    @Transactional(readOnly = true)
    fun obtenerDetalleAnimal(idAnimal: Int): AnimalListItemResponse? {
        logger.info("Obteniendo detalle de animal: idAnimal=$idAnimal")
        val animal = animalRepository.findByIdAnimal(idAnimal) ?: return null
        if (animal.publicacion.estado == "Borrada") return null
        return AnimalListItemResponse.from(animal)
    }

    /**
     * Obtiene los datos editables de un animal.
     * Solo el dueño de la publicación puede acceder.
     * @param token Token de sesión del usuario.
     * @param idAnimal ID del animal a consultar.
     * @return [AnimalDetailResponse] con los campos editables.
     */
    @Transactional(readOnly = true)
    fun obtenerAnimalParaEditar(token: String, idAnimal: Int): AnimalDetailResponse {
        logger.info("Obteniendo animal para editar: idAnimal=$idAnimal")
        val usuario = usuarioService.getMe(token) ?: throw Exception("Token inválido")
        val animal = animalRepository.findByIdAnimalAndIdUsuario(idAnimal, usuario.id.toInt())
            ?: throw Exception("Animal no encontrado o no eres el dueño")

        return AnimalDetailResponse(
            idAnimal = animal.idAnimal,
            nombre = animal.nombre,
            tipo = animal.tipo,
            edad = animal.edad,
            descripcion = animal.descripcion,
            codigoPostal = animal.codigoPostal,
            estadoVacunacion = animal.estadoVacunacion,
            esterilizado = animal.esterilizado,
            entrenado = animal.entrenado,
            idRaza = animal.raza?.id ?: 0,
            overrideEnergia = animal.overrideEnergia,
            overrideIndependencia = animal.overrideIndependencia,
            overrideSociableNiños = animal.overrideSociableNiños,
            overrideSociableMascotas = animal.overrideSociableMascotas,
            padecimientos = animal.padecimientos,
            fotos = animal.fotos
        )
    }

    /**
     * Actualiza los campos de un animal.
     * Solo el dueño puede editar.
     * @param token Token de sesión del usuario.
     * @param idAnimal ID del animal a editar.
     * @param request [AnimalUpdateRequest] con los campos a actualizar.
     * @return La entidad [AnimalEntity] actualizada.
     */
    @Transactional
    fun editarAnimal(token: String, idAnimal: Int, request: AnimalUpdateRequest): AnimalEntity {
        logger.info("Editando animal: idAnimal=$idAnimal")
        val usuario = usuarioService.getMe(token) ?: throw Exception("Token inválido")
        val animal = animalRepository.findByIdAnimalAndIdUsuario(idAnimal, usuario.id.toInt())
            ?: throw Exception("Animal no encontrado o no eres el dueño")

        request.nombre?.trim()?.let { animal.nombre = it }
        request.tipo?.let { animal.tipo = it }
        request.edad?.let { animal.edad = it }
        request.descripcion?.trim()?.let { animal.descripcion = it }
        request.codigoPostal?.let { animal.codigoPostal = it }
        request.estadoVacunacion?.let { animal.estadoVacunacion = it }
        request.esterilizado?.let { animal.esterilizado = it }
        request.entrenado?.let { animal.entrenado = it }
        request.idRaza?.let {
            animal.raza = razaRepository.findById(it)
                .orElseThrow { RuntimeException("Raza no encontrada") }
        }
        request.overrideEnergia?.let { animal.overrideEnergia = it }
        request.overrideIndependencia?.let { animal.overrideIndependencia = it }
        request.overrideSociableNiños?.let { animal.overrideSociableNiños = it }
        request.overrideSociableMascotas?.let { animal.overrideSociableMascotas = it }
        request.padecimientos?.let {
            animal.padecimientos = it.map(String::trim).filter(String::isNotEmpty).toMutableSet()
        }
        request.fotos?.let {
            animal.fotos = it.map(String::trim).filter(String::isNotEmpty).toMutableSet()
        }
        return animalRepository.save(animal)
    }

    /**
     * Cambia el estado de una publicación (Borrada, Adoptado, etc.).
     * Solo el dueño puede realizarlo.
     * @param token Token de sesión del usuario.
     * @param idPublicacion ID de la publicación.
     * @param nuevoEstado Nuevo estado a asignar.
     */
    @Transactional
    fun cambiarEstadoPublicacion(token: String, idPublicacion: Int, nuevoEstado: String) {
        logger.info("Cambiando estado de publicación: idPublicacion=$idPublicacion a $nuevoEstado")
        val usuario = usuarioService.getMe(token) ?: throw Exception("Token inválido")
        val publicacion = publicacionRepository
            .findByIdPublicacionAndIdUsuario(idPublicacion, usuario.id.toInt())
            ?: throw Exception("Publicación no encontrada o no eres el dueño")
        publicacion.estado = nuevoEstado
        publicacionRepository.save(publicacion)
    }


    /**
     * Recomienda animales en adopción compatibles con el perfil del usuario autenticado.
     *
     * Aplica un algoritmo de dos capas:
     *
     * Capa 1 — Filtro geográfico:
     *   Filtra publicaciones activas cuyo código postal difiere en menos de 5000 unidades
     *   respecto al código postal del usuario. Esto aproxima proximidad geográfica sin
     *   necesitar coordenadas GPS.
     *
     * Capa 2 — K-Radius por distancia euclidiana ponderada:
     *   Compara el vector de preferencias del usuario (obtenido de su último cuestionario)
     *   contra el vector de atributos de cada animal. Los atributos del animal priorizan
     *   los overrides individuales sobre los valores por defecto de la raza.
     *
     *   Vector del usuario:
     *     [tiempoEjercicio, tiempoSoledad, targetSocNiños, targetSocMascotas]
     *
     *   Vector del animal:
     *     [nivelEnergia, independencia, sociableNiños, sociableMascotas]
     *
     *   La distancia euclidiana se calcula con pesos diferenciados:
     *     - nivelEnergia e independencia tienen peso 2.0 (más determinantes)
     *     - sociableNiños y sociableMascotas tienen peso 1.5
     *
     *   El score de compatibilidad se define como:
     *     score = 1 / (1 + distancia)
     *   donde score=1.0 es compatibilidad perfecta y score→0 es incompatibilidad total.
     *
     *   Solo se devuelven animales con score >= 0.4 (umbral de compatibilidad mínima),
     *   ordenados de mayor a menor compatibilidad.
     *
     * Filtros adicionales:
     *   - Si el usuario tiene alergias (tieneAlergias=1), solo se consideran animales
     *     de razas hipoalergénicas (esHipoalergenico=1).
     *   - Si el usuario no ha completado el cuestionario, se devuelve lista vacía.
     *
     * @param token Token de sesión del usuario autenticado.
     * @return Lista de [AnimalListItemResponse] ordenada por score descendente,
     *         o lista vacía si no hay cuestionario o no hay candidatos compatibles.
     */
    fun getRecomendados(token: String): List<AnimalListItemResponse> {
        val usuario = usuarioService.getMe(token)
            ?: throw Exception("Token inválido")

        // Obtener último formulario
        val formularios = formularioRepository.findAllByUsuarioId(usuario.id)
        if (formularios.isEmpty()) return emptyList()
        val formulario = formularios.maxByOrNull { it.fechaEnvio }!!

        // Capa 1: filtrar publicaciones activas por CP cercano
        val cpUsuario = usuario.codigoPostal.toIntOrNull() ?: return emptyList()
        var candidatos = animalRepository.findAll()
            .filter { it.publicacion.estado == "Activa" }
            .filter {
                val cpAnimal = it.codigoPostal.toIntOrNull() ?: return@filter false
                kotlin.math.abs(cpAnimal - cpUsuario) <= 5000
            }

        // Filtro duro: alergias
        if (formulario.tieneAlergias == 1) {
            candidatos = candidatos.filter { it.raza.esHipoalergenico == 1 }
        }

        if (candidatos.isEmpty()) return emptyList()

        // Normalizar respuestas del formulario al espacio 1-5
        val targetEnergia = formulario.tiempoEjercicio.toDouble()
        val targetIndependencia = formulario.tiempoSoledad.toDouble()
        val targetSocNiños = if (formulario.`tieneNiños` == 1) 5.0 else 3.0
        val targetSocMascotas = if (formulario.tieneMascotas == 1) 5.0 else 3.0

        // Pesos — ejercicio y soledad más determinantes
        val pesoEnergia = 2.0
        val pesoIndependencia = 2.0
        val pesoSocNiños = 1.5
        val pesoSocMascotas = 1.5

        data class AnimalScore(val animal: AnimalEntity, val score: Double)

        // Calcular score por distancia euclidiana ponderada
        val scored = candidatos.map { animal ->
            val energia = (animal.overrideEnergia?: animal.raza.nivelEnergia).toDouble()
            val independ = (animal.overrideIndependencia?: animal.raza.independencia).toDouble()
            val socNiños = (animal.overrideSociableNiños?: animal.raza.sociableNiños).toDouble()
            val socMascotas = (animal.overrideSociableMascotas?: animal.raza.sociableMascotas).toDouble()

            val distancia = Math.sqrt(
                pesoEnergia * Math.pow(targetEnergia- energia, 2.0) +
                        pesoIndependencia*Math.pow(targetIndependencia  - independ,    2.0) +
                        pesoSocNiños*Math.pow(targetSocNiños      - socNiños,    2.0) +
                        pesoSocMascotas*Math.pow(targetSocMascotas   - socMascotas, 2.0)
            )

            AnimalScore(animal, 1.0 / (1.0 + distancia))
        }

        // K-radius: solo animales con score >= umbral, ordenados por compatibilidad
        val UMBRAL_SCORE = 0.5

        return scored
            .filter { it.score >= UMBRAL_SCORE }
            .sortedByDescending { it.score }
            .map { AnimalListItemResponse.from(it.animal) }
    }
}