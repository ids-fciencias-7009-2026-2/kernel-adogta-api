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

    /**
     * Notifica al donante que un adoptante expresó interés en la publicación de su mascota.
     *
     * Manda un mensaje en HTML con los datos del animal publicado y los datos de contacto
     * del adoptante para que el donante pueda continuar el proceso de adopción.
     *
     * @param destinatario     Correo electrónico del usuario donante (publicador).
     * @param nombreAnimal     Nombre del animal sobre el que se expresó interés.
     * @param nombreAdoptante  Nombre completo del usuario adoptante.
     * @param emailAdoptante   Correo electrónico del adoptante.
     * @param telefonoAdoptante Teléfono del adoptante (puede ser nulo si no lo registró).
     */
    fun enviarCorreoSolicitudAdopcion(
        destinatario: String,
        nombreAnimal: String,
        nombreAdoptante: String,
        emailAdoptante: String,
        telefonoAdoptante: String?
    )
    fun enviarCorreoSeleccionado(destinatario: String, nombreAnimal: String)

    fun enviarCorreoNoSeleccionado(destinatario: String, nombreAnimal: String)

    /**
     * Corrreo de notificación de usuario baneado
     * 
     * Manda un mensaej en HTML que le expresa al usuario las razones de la suspensión de su cuenta.
     * 
     * @param destinatario  Correo electŕonico del usuario baneado.
     * @param motivo        Descripción de las razones del baneo.
     */
    fun enviarNotificacionBaneo(destinatario: String, motivo: String)
}