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
        val enlace = "$baseUrl/reset-password?token=$token"

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

    /**
     * Envía un correo al donante notificándole que un adoptante expresó interés
     * en la publicación de su mascota.
     *
     * Si el envío falla:
     * Spring lanzará una excepción org.springframework.mail.MailSendException
     *
     * @param destinatario      Correo electrónico del usuario donante.
     * @param nombreAnimal      Nombre del animal publicado.
     * @param nombreAdoptante   Nombre completo del adoptante.
     * @param emailAdoptante    Correo electrónico del adoptante.
     * @param telefonoAdoptante Teléfono del adoptante (puede ser nulo).
     */
    override fun enviarCorreoSolicitudAdopcion(
        destinatario: String,
        nombreAnimal: String,
        nombreAdoptante: String,
        emailAdoptante: String,
        telefonoAdoptante: String?
    ) {
        // Construir el contexto para la plantilla Thymeleaf
        val context = Context().apply {
            setVariable("nombreAnimal", nombreAnimal)
            setVariable("nombreAdoptante", nombreAdoptante)
            setVariable("emailAdoptante", emailAdoptante)
            setVariable("telefonoAdoptante", telefonoAdoptante)
        }

        // Procesar la plantilla HTML -> String
        val contenidoHtml = templateEngine.process("email-solicitud-adopcion", context)

        // Construir el mensaje
        val mensaje: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mensaje, true, "UTF-8")

        //contenido del correo.
        helper.setTo(destinatario)
        helper.setSubject("Nueva solicitud de adopción para $nombreAnimal - Adogta")
        helper.setText(contenidoHtml, true)

        // Se envia el correo.
        mailSender.send(mensaje)
        logger.info("Correo de solicitud de adopción enviado a $destinatario (animal: $nombreAnimal)")
    }

    override fun enviarCorreoSeleccionado(destinatario: String, nombreAnimal: String) {
        val context = Context().apply {
            setVariable("nombreAnimal", nombreAnimal)
        }
        val contenidoHtml = templateEngine.process("email-tramite-iniciado-seleccionado", context)
        val mensaje: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mensaje, true, "UTF-8")
        helper.setTo(destinatario)
        helper.setSubject("¡Fuiste seleccionado para adoptar a $nombreAnimal! - Adogta")
        helper.setText(contenidoHtml, true)
        mailSender.send(mensaje)
        logger.info("Correo tramite seleccionado enviado a $destinatario")
    }

    override fun enviarCorreoNoSeleccionado(destinatario: String, nombreAnimal: String) {
        val context = Context().apply {
            setVariable("nombreAnimal", nombreAnimal)
        }
        val contenidoHtml = templateEngine.process("email-tramite-iniciado-no-seleccionado", context)
        val mensaje: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mensaje, true, "UTF-8")
        helper.setTo(destinatario)
        helper.setSubject("Actualización sobre $nombreAnimal - Adogta")
        helper.setText(contenidoHtml, true)
        mailSender.send(mensaje)
        logger.info("Correo tramite no seleccionado enviado a $destinatario")
    }

    override fun enviarNotificacionBaneo(destinatario: String, motivo: String) {
        val context = Context().apply {
            setVariable("motivo", motivo)
        }
        val contenidoHtml = templateEngine.process("email-baneo", context)

        val mensaje = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mensaje, true, "UTF-8")
        helper.setTo(destinatario)
        helper.setSubject("Tu cuenta ha sido suspendida - Adogta")
        helper.setText(contenidoHtml, true)

        mailSender.send(mensaje)
        logger.info("Correo de baneo enviado a $destinatario")
    }
}