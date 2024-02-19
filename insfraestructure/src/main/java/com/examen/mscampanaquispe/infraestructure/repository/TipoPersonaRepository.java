package com.examen.mscampanaquispe.infraestructure.repository;

import com.examen.mscampanaquispe.infraestructure.entity.TipoPersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoPersonaRepository extends JpaRepository<TipoPersonaEntity, Long> {
    TipoPersonaEntity findByCodTipo(String tipoPersona);
}
