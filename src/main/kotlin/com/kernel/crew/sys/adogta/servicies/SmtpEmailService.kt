package com.kernel.crew.sys.adogta.servicies

import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

/**
 * Implementación de EmailService que envía los correos mediante JavaMailSender.
 *
 * Utiliza Thymeleaf para las plantillas con el contenido HTML de los correos.
 *
 * @property mailSender     Spring Boot Mail auto-configuration.
 * @property templateEngine Thymeleaf para procesar los archivos HTML.
 * @property baseUrl        URL base de la aplicación.
 */
@Component
class SmtpEmailService(
    private val mailSender: JavaMailSender,
    private val templateEngine: TemplateEngine,
    @Value("\${app.base-url}") private val baseUrl: String
) : EmailService {

    private val logger = LoggerFactory.getLogger(SmtpEmailService::class.java)

    /**
     * Envía un correo de recuperación de contraseña con un enlace que contiene el token.
     *
     * Si el envío falla: 
     * Spring lanzará una excepción org.springframework.mail.MailSendException
     *
     * @param destinatario Dirección de correo electrónico del usuario.
     * @param token        Token de recuperación.
     */
    override fun enviarCorreoRecuperacion(destinatario: String, token: String) {
        val enlace = "$baseUrl/usuarios/reset-password?token=$token"

        // Construir el contexto para la plantilla Thymeleaf
        val context = Context().apply {
            setVariable("enlace", enlace)
        }

        // Procesar la plantilla HTML -> String
        val contenidoHtml = templateEngine.process("email-recuperacion", context)

        // Construir el mensaje
        val mensaje: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mensaje, true, "UTF-8")

        //contenido del correo.
        helper.setTo(destinatario)
        helper.setSubject("Recuperación de contraseña - Adogta")
        helper.setText(contenidoHtml, true)

        // Se envia el correo.
        mailSender.send(mensaje)
        logger.info("Correo de recuperación enviado a $destinatario")
    }
}