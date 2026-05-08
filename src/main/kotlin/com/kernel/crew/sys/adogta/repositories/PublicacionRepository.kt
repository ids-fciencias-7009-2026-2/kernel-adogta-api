package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.PublicacionEntity
import com.kernel.crew.sys.adogta.entities.PublicacionId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repositorio JPA para la entidad [PublicacionEntity].
 */
@Repository
interface PublicacionRepository : JpaRepository<PublicacionEntity, PublicacionId> {

    /**
     * Busca una publicación por su ID y el dueño, para verificar propiedad.
     * @param idPublicacion ID de la publicación.
     * @param idUsuario ID del usuario dueño.
     * @return La publicación si pertenece al usuario, null en caso contrario.
     */
    fun findByIdPublicacionAndIdUsuario(idPublicacion: Int, idUsuario: Int): PublicacionEntity?

    /**
     * Lista todas las publicaciones de un usuario.
     * @param idUsuario ID del usuario.
     * @return Lista de publicaciones asociadas.
     */
    fun findByIdUsuario(idUsuario: Int): List<PublicacionEntity>
}