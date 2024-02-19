package com.examen.mscampanaquispe.application.controller;

import com.examen.mscampanaquispe.domain.aggregates.dto.PersonaDTO;
import com.examen.mscampanaquispe.domain.aggregates.request.RequestPersona;
import com.examen.mscampanaquispe.domain.ports.in.PersonaServiceIn;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@OpenAPIDefinition(
        info = @Info(
                title = "API_PERSONA",
                version = "1.0",
                description = "Mantenimiento de una Persona"
        )
)
@RestController
@RequestMapping("/v2/persona")
@RequiredArgsConstructor
public class PersonaController {
    private final PersonaServiceIn personaServiceIn;

    @Operation(summary = "Crea una persona")
    @PostMapping
    public ResponseEntity<PersonaDTO> registrar(@RequestBody RequestPersona requestPersona) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.crearPersonaIn(requestPersona));
    }
    @Operation(summary = "Busca una persona por el numero de documento")
    @GetMapping("/{numDocu}")
    public ResponseEntity<PersonaDTO> obtenerPersona(@PathVariable String numDocu) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.obtenerPersonaIn(numDocu).get());
    }
    @Operation(summary = "Lista todas las personas")
    @GetMapping()
    public ResponseEntity<List<PersonaDTO>> listarPersonasActivas() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.obtenerTodosIn());
    }
    @Operation(summary = "Actualiza una persona")
    @PutMapping("/{id}")
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody RequestPersona requestPersona) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.actualizarIn(id, requestPersona));
    }

    @Operation(summary = "Anula una persona")
    @DeleteMapping("/{id}")
    public ResponseEntity anular(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.deleteIn(id));
    }

}
