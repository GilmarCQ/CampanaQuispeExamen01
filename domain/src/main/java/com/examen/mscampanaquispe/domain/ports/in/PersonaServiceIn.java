package com.examen.mscampanaquispe.domain.ports.in;

import com.examen.mscampanaquispe.domain.aggregates.dto.PersonaDTO;
import com.examen.mscampanaquispe.domain.aggregates.request.RequestPersona;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceIn {

    PersonaDTO crearPersonaIn(RequestPersona requestPersona);
    Optional<PersonaDTO> obtenerPersonaIn(String numDocu);
    List<PersonaDTO> obtenerTodosIn();
    PersonaDTO actualizarIn(Long id, RequestPersona requestPersona);
    PersonaDTO deleteIn(Long id);
}
