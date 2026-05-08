package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.AnimalEntity
import com.kernel.crew.sys.adogta.entities.AnimalId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repositorio JPA para la entidad [AnimalEntity].
 * Proporciona consultas.
 */
@Repository
interface AnimalRepository : JpaRepository<AnimalEntity, AnimalId> {

    /**
     * Busca un animal por su ID y el dueño, para verificar propiedad.
     * @param idAnimal ID del animal.
     * @param idUsuario ID del usuario dueño.
     * @return El animal si pertenece al usuario, null en caso contrario.
     */
    fun findByIdAnimalAndIdUsuario(idAnimal: Int, idUsuario: Int): AnimalEntity?

    /**
     * busca una publicación por ID.
     * @param idPublicacion ID de la publicación.
     * @return Annimal(es) asociado.
     */
    fun findByIdPublicacion(idPublicacion: Int): List<AnimalEntity>
}