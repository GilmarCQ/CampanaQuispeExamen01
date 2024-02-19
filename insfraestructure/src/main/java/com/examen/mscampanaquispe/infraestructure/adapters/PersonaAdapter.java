package com.examen.mscampanaquispe.infraestructure.adapters;

import com.examen.mscampanaquispe.domain.aggregates.constants.Constants;
import com.examen.mscampanaquispe.domain.aggregates.dto.PersonaDTO;
import com.examen.mscampanaquispe.domain.aggregates.request.RequestPersona;
import com.examen.mscampanaquispe.domain.aggregates.response.ResponseReniec;
import com.examen.mscampanaquispe.domain.ports.out.PersonaServiceOut;
import com.examen.mscampanaquispe.infraestructure.entity.PersonaEntity;
import com.examen.mscampanaquispe.infraestructure.entity.TipoDocumentoEntity;
import com.examen.mscampanaquispe.infraestructure.entity.TipoPersonaEntity;
import com.examen.mscampanaquispe.infraestructure.mapper.PersonaMapper;
import com.examen.mscampanaquispe.infraestructure.repository.PersonaRepository;
import com.examen.mscampanaquispe.infraestructure.repository.TipoDocumentoRepository;
import com.examen.mscampanaquispe.infraestructure.repository.TipoPersonaRepository;
import com.examen.mscampanaquispe.infraestructure.rest.client.ClienteReniec;
import com.examen.mscampanaquispe.infraestructure.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {

    private final PersonaRepository personaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final TipoPersonaRepository tipoPersonaRepository;
    private final PersonaMapper personaMapper;
    private final ClienteReniec reniec;
    private final Util util;

    @Value("${token.api}")
    private String tokenApi;

    @Override
    public PersonaDTO crearPersonaOut(RequestPersona requestPersona) {
        //  Validar Tipo Documento, Tipo Persona Duplicado
        if (findPersonaBD(requestPersona)) {
            return null;
        }
        ResponseReniec datosReniec = getExecutionReniec(requestPersona.getNumDoc());
        personaRepository.save(getEntity(datosReniec, requestPersona));
        return personaMapper.mapToDto(getEntity(datosReniec, requestPersona));
    }

    @Override
    public Optional<PersonaDTO> obtenerPersonaOut(String numDocu) {
        return Optional.ofNullable(personaMapper.mapToDto(personaRepository.findByNumDocu(numDocu).get()));
    }

    @Override
    public List<PersonaDTO> obtenerTodosOut() {
        List<PersonaDTO> personaDTOList = new ArrayList<>();
        List<PersonaEntity> entities = personaRepository.findAllPersonasActivas();
        for (PersonaEntity persona : entities) {
            PersonaDTO personaDTO = personaMapper.mapToDto(persona);
            personaDTOList.add(personaDTO);
        }
        return personaDTOList;
    }

    @Override
    public PersonaDTO actualizarOut(Long id, RequestPersona requestPersona) {
        boolean exists = personaRepository.existsById(id);
        if (exists) {
            Optional<PersonaEntity> entity = personaRepository.findById(id);
            ResponseReniec responseReniec = getExecutionReniec(requestPersona.getNumDoc());
            personaRepository.save(getEntityUpdate(responseReniec,entity.get(), requestPersona));
            return personaMapper.mapToDto(getEntityUpdate(responseReniec,entity.get(), requestPersona));
        }
        return null;
    }

    @Override
    public PersonaDTO deleteOut(Long id) {
        boolean exists = personaRepository.existsById(id);
        if (exists) {
            Optional<PersonaEntity> entity = personaRepository.findById(id);
            PersonaEntity personaDB = personaRepository.save(getEntityDelete(entity.get()));
            return personaMapper.mapToDto(personaDB);
        }
        return null;
    }


    //  UTILS
    public Boolean findPersonaBD(RequestPersona requestPersona) {
        TipoDocumentoEntity tipoDocumentoBd = tipoDocumentoRepository.findByCodTipo(requestPersona.getTipoDoc());
        TipoPersonaEntity tipoPersonaBd = tipoPersonaRepository.findByCodTipo(requestPersona.getTipoPer());
        Optional<PersonaEntity> personaBD = personaRepository.findByNumDocuActivo(
                requestPersona.getNumDoc(), tipoDocumentoBd.getIdTipoDocumento(), tipoPersonaBd.getIdTipoPersona());
        return !personaBD.isEmpty();
    }
    public ResponseReniec getExecutionReniec(String numero){
        String authorization = "Bearer " + tokenApi;
        ResponseReniec responseReniec = reniec.getInfoReniec(numero,authorization);
        return  responseReniec;
    }
    private PersonaEntity getEntity(ResponseReniec reniec, RequestPersona requestPersona){
        TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByCodTipo(requestPersona.getTipoDoc());
        TipoPersonaEntity tipoPersona = tipoPersonaRepository.findByCodTipo(requestPersona.getTipoPer());
        PersonaEntity entity = new PersonaEntity();
        entity.setNumDocu(reniec.getNumeroDocumento());
        entity.setNombres(reniec.getNombres());
        entity.setApePat(reniec.getApellidoPaterno());
        entity.setApeMat(reniec.getApellidoMaterno());
        entity.setEstado(Constants.STATUS_ACTIVE);
        entity.setUsuaCrea(Constants.AUDIT_ADMIN);
        entity.setDateCreate(getTimestamp());
        entity.setTipoDocumento(tipoDocumento);
        entity.setTipoPersona(tipoPersona);
        return entity;
    }
    private PersonaEntity getEntityUpdate(ResponseReniec reniec, PersonaEntity personaActualizar, RequestPersona requestPersona){
        TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByCodTipo(requestPersona.getTipoDoc());
        TipoPersonaEntity tipoPersona = tipoPersonaRepository.findByCodTipo(requestPersona.getTipoPer());
        personaActualizar.setNombres(reniec.getNombres());
        personaActualizar.setApePat(reniec.getApellidoPaterno());
        personaActualizar.setApeMat(reniec.getApellidoMaterno());
        personaActualizar.setUsuaModif(Constants.AUDIT_ADMIN);
        personaActualizar.setDateModif(getTimestamp());
        personaActualizar.setNumDocu(requestPersona.getNumDoc());
        personaActualizar.setTipoDocumento(tipoDocumento);
        personaActualizar.setTipoPersona(tipoPersona);
        return personaActualizar;
    }
    private PersonaEntity getEntityDelete(PersonaEntity personaActualizar){
        personaActualizar.setUsuaDelet(Constants.AUDIT_ADMIN);
        personaActualizar.setDateDelet(getTimestamp());
        personaActualizar.setEstado(0);
        return personaActualizar;
    }
    private Timestamp getTimestamp(){
        long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        return timestamp;
    }
}
