package com.kernel.crew.sys.adogta.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Convierte los errores de @Valid en una respuesta 400 con el mismo
 * shape que el resto del API: { "error": "..." }, además de un mapa
 * fields para que el cliente pueda señalar el campo inválido.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val fieldErrors = ex.bindingResult.fieldErrors
            .associate { it.field to (it.defaultMessage ?: "Valor inválido.") }
        val firstMessage = fieldErrors.values.firstOrNull() ?: "Datos inválidos."
        logger.warn("Validación fallida: $fieldErrors")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf("error" to firstMessage, "fields" to fieldErrors)
        )
    }
}
