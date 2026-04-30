package com.kernel.crew.sys.adogta.servicies

/**
 * Interfaz para envio de correos eletronicos desde la plataforma.
 * 
 * Define las operaciones de notificación por correo que el sistema va a realizar,
 * pero desacoplando la lógica concreta del envío.
 */
interface EmailService {

    /**
     * Envía un correo electrónico con las instrucciones para restablecer la contraseña.
     *
     * manda un mensaje en HTML que incluye un enlace con un token
     * y le permite al usuario crear una nueva contraseña.
     *
     * @param destinatario Dirección de correo electrónico del usuario que solicitó la recuperación.
     * @param token        Token único (UUID) que identifica la solicitud y se incluye en el enlace.
     */
    fun enviarCorreoRecuperacion(destinatario: String, token: String)
}