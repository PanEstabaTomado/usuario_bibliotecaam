package dfy1103.maq.usuario.controller;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import dfy1103.maq.usuario.assembler.UsuarioModelAssembler;
import dfy1103.maq.usuario.dto.UsuarioRequestDTO;
import dfy1103.maq.usuario.dto.UsuarioResponseDTO;
import dfy1103.maq.usuario.model.Usuario;
import dfy1103.maq.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/bibliotecaam/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones asociadas a usuarios.")
public class UsuarioController {
    private final UsuarioService usuarioService;
    @Autowired
    private UsuarioModelAssembler assembler;
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene una lista de todos los usuarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<CollectionModel<EntityModel<UsuarioResponseDTO>>> obtenerTodas(){
        List<EntityModel<UsuarioResponseDTO>> asistencias = usuarioService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(asistencias,
                linkTo(methodOn(UsuarioController.class).obtenerTodas()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener usuario por id.", description = "Obtiene una lista de todos los usuarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> obtenerPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Guardar un usuario", description = "Guarda un usuario acorde a lo ingresado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "400", description = "Error al ingresar parametros. Revise si ingreso todos los parametros solicitados."),
            @ApiResponse(responseCode = "403", description = "No tienes permiso para hacer el cambio.")
    })
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> guardar(@Valid @RequestBody UsuarioRequestDTO doto){
        UsuarioResponseDTO nuevaAsistencia = usuarioService.guardar(doto);
        return ResponseEntity.status(201).body(assembler.toModel(nuevaAsistencia));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario acorde a una id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El id del usuario no existe.")
    })
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO doto){
        return usuarioService.actualizar(id, doto)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario acorde a una id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Usuario eliminado con exito!"),
            @ApiResponse(responseCode = "404",description = "ERROR: ¡El id del usuario ingresado no existe!")
    })
    public ResponseEntity<Map<String,String>> eliminar(@PathVariable Long id){
        if (usuarioService.buscarPorId(id).isEmpty()){
            Map<String, String> borrado = new LinkedHashMap<>();
            borrado.put("¡ERROR! ", "¡El usuario con id "+id+" no fue encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(borrado);
        } else {
            Map<String, String> borrado = new LinkedHashMap<>();
            borrado.put("¡EXITO! ", "¡El usuario fue eliminado con exito!");
            return ResponseEntity.status(HttpStatus.OK).body(borrado);
        }
    }
}
