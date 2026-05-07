package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.servicies.UsuarioService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID

/**
 * Controlador REST que maneja la subida de archivos al servidor.
 *
 * Los archivos se almacenan en el directorio 'uploads/' con un nombre único (UUID).
 * Solo usuarios con sesión activa pueden subir archivos.
 *
 * Toda la validación de sesión está delegada a [UsuarioService].
 */
@RestController
@RequestMapping("/api/upload")
class UploadController {

    private val logger = LoggerFactory.getLogger(UploadController::class.java)

    @Autowired
    lateinit var usuarioService: UsuarioService

    private val uploadDir: Path = Paths.get("uploads").toAbsolutePath().also {
        Files.createDirectories(it)
    }

    /**
     * Sube un archivo al servidor y retorna la URL pública donde puede ser accedido.
     *
     * Endpoint protegido: requiere un token válido en el header 'Authorization'.
     *
     * @param token Token de sesión enviado en el header 'Authorization'.
     * @param file Archivo a subir enviado como multipart/form-data.
     * @return 201 con la URL pública del archivo subido,
     *         400 si el archivo está vacío,
     *         o 401 si el token es nulo, inválido o la sesión expiró.
     */
    @PostMapping
    fun upload(
        @RequestHeader("Authorization", required = false) token: String?,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Any> {
        logger.info("POST /api/upload - ${file.originalFilename ?: "(sin nombre)"}")

        if (token == null) return ResponseEntity.status(401).build()

        usuarioService.getMe(token)
            ?: return ResponseEntity.status(401).build()

        if (file.isEmpty) return ResponseEntity.status(400).body(mapOf("error" to "Archivo vacío"))

        val extension = file.originalFilename?.substringAfterLast('.', "") ?: ""
        val storedName = "${UUID.randomUUID()}${if (extension.isNotBlank()) ".$extension" else ""}"
        file.inputStream.use { Files.copy(it, uploadDir.resolve(storedName)) }

        logger.info("Archivo guardado: $storedName")
        return ResponseEntity.status(HttpStatus.CREATED).body(mapOf("url" to "http://localhost:8080/uploads/$storedName"))
    }
}