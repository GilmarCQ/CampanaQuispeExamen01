package com.examen.mscampanaquispe.infraestructure.repository;

import com.examen.mscampanaquispe.infraestructure.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {
    Optional<PersonaEntity> findByNumDocu(String numDoc);

    Boolean existsByNumDocu(String numDocu);

    @Query(
            value = "SELECT * FROM persona p where p.estado = 1",
            nativeQuery = true)
    List<PersonaEntity> findAllPersonasActivas();

    @Query(
            value = "SELECT * FROM persona p where p.estado = 1 and num_docu = ?1 and tipo_documento_id = ?2 and tipo_persona_id = ?3",
            nativeQuery = true
    )
    Optional<PersonaEntity> findByNumDocuActivo(String numDocu, Long idTipoDocumento, Long idTipoPersona);
}
