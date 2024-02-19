package com.examen.mscampanaquispe.infraestructure.mapper;

import com.examen.mscampanaquispe.domain.aggregates.dto.PersonaDTO;
import com.examen.mscampanaquispe.infraestructure.entity.PersonaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonaMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public PersonaDTO mapToDto(PersonaEntity entity) {
        return modelMapper.map(entity, PersonaDTO.class);
    }

    public PersonaEntity mapToEntity(PersonaDTO personaDTO) {
        return modelMapper.map(personaDTO, PersonaEntity.class);
    }
}
