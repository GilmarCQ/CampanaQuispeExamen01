package com.examen.mscampanaquispe.domain.ports.out;

import com.examen.mscampanaquispe.domain.aggregates.dto.PersonaDTO;
import com.examen.mscampanaquispe.domain.aggregates.request.RequestPersona;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceOut {
    PersonaDTO crearPersonaOut(RequestPersona requestPersona);
    Optional<PersonaDTO> obtenerPersonaOut(String numDocu);
    List<PersonaDTO> obtenerTodosOut();
    PersonaDTO actualizarOut(Long id, RequestPersona requestPersona);
    PersonaDTO deleteOut(Long id);
}
