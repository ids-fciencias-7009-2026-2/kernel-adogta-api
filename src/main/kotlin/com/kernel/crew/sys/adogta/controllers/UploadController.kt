package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.servicies.UsuarioService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = ["http://localhost:5173"])
class UploadController(
    private val usuarioService: UsuarioService
) {

    private val logger = LoggerFactory.getLogger(UploadController::class.java)

    private val uploadDir: Path = Paths.get("uploads").toAbsolutePath().also {
        Files.createDirectories(it)
    }

    @PostMapping
    fun upload(
        @RequestHeader("Authorization", required = false) token: String?,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Any> {
        logger.info("POST /api/upload - ${file.originalFilename ?: "(sin nombre)"}")
        if (token == null) {
            logger.warn("POST /api/upload sin token de sesión")
            return ResponseEntity.status(401).build()
        }
        if (usuarioService.getMe(token) == null) {
            logger.warn("POST /api/upload con token inválido o expirado")
            return ResponseEntity.status(401).build()
        }
        if (file.isEmpty) {
            logger.warn("POST /api/upload con archivo vacío")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "Archivo vacío"))
        }

        val originalName = file.originalFilename ?: "archivo"
        val extension = originalName.substringAfterLast('.', "")
        val storedName = "${UUID.randomUUID()}${if (extension.isNotBlank()) ".$extension" else ""}"
        val target = uploadDir.resolve(storedName)
        file.inputStream.use { Files.copy(it, target) }

        val url = "http://localhost:8080/uploads/$storedName"
        logger.info("Archivo guardado: $storedName")
        return ResponseEntity.status(HttpStatus.CREATED).body(mapOf("url" to url))
    }
}
