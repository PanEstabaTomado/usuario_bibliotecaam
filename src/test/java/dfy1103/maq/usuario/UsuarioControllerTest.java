package dfy1103.maq.usuario;

import dfy1103.maq.usuario.assembler.UsuarioModelAssembler;
import dfy1103.maq.usuario.controller.UsuarioController;
import dfy1103.maq.usuario.dto.UsuarioRequestDTO;
import dfy1103.maq.usuario.dto.UsuarioResponseDTO;
import dfy1103.maq.usuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@ActiveProfiles("test")
@Import(UsuarioModelAssembler.class)
@DisplayName("Tests Unitarios - UsuarioController")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName("GIVEN: Existen usuarios WHEN: GET /api/bibliotecaam/usuario THEN: Retorna 200 OK y la lista HAL-JSON")
    void shouldReturnTodosLosUsuarios() throws Exception {
        UsuarioResponseDTO u1 = new UsuarioResponseDTO(1L, 12345678, "K", "Juan", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));
        UsuarioResponseDTO u2 = new UsuarioResponseDTO(2L, 87654321, "0", "María", null, "López", "Silva", LocalDate.of(1995, 8, 25));
        List<UsuarioResponseDTO> lista = Arrays.asList(u1, u2);

        Mockito.when(usuarioService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/bibliotecaam/usuario")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioResponseDTOList.length()").value(2))
                .andExpect(jsonPath("$._embedded.usuarioResponseDTOList[0].idUsuario").value(1L))
                .andExpect(jsonPath("$._embedded.usuarioResponseDTOList[0].pnombreUsu").value("Juan"))
                .andExpect(jsonPath("$._embedded.usuarioResponseDTOList[1].numrunUsu").value(87654321));
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: GET /api/bibliotecaam/usuario/{id} THEN: Retorna 200 OK y el usuario")
    void shouldReturnUsuarioById() throws Exception {
        Long id = 1L;
        UsuarioResponseDTO mockResponse = new UsuarioResponseDTO(id, 12345678, "K", "Juan", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));

        Mockito.when(usuarioService.buscarPorId(id)).thenReturn(Optional.of(mockResponse));

        mockMvc.perform(get("/api/bibliotecaam/usuario/{id}", id)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(id))
                .andExpect(jsonPath("$.pnombreUsu").value("Juan"))
                .andExpect(jsonPath("$.numrunUsu").value(12345678));
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: GET /api/bibliotecaam/usuario/{id} THEN: Retorna 404 Not Found")
    void shouldReturnNotFoundWhenUsuarioDoesNotExist() throws Exception {
        Long id = 99L;
        Mockito.when(usuarioService.buscarPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bibliotecaam/usuario/{id}", id)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GIVEN: Request válido WHEN: POST /api/bibliotecaam/usuario THEN: Retorna 201 Created")
    void shouldCreateUsuario() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO(12345678, "K", "Juan", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));
        UsuarioResponseDTO mockResponse = new UsuarioResponseDTO(1L, 12345678, "K", "Juan", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));

        Mockito.when(usuarioService.guardar(any(UsuarioRequestDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/bibliotecaam/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(1L))
                .andExpect(jsonPath("$.pnombreUsu").value("Juan"))
                .andExpect(jsonPath("$.fechaNacUsu").value("1990-05-10"));
    }

    @Test
    @DisplayName("GIVEN: ID y Request válido WHEN: PUT /api/bibliotecaam/usuario/{id} THEN: Retorna 200 OK")
    void shouldUpdateUsuario() throws Exception {
        Long id = 1L;
        UsuarioRequestDTO request = new UsuarioRequestDTO(12345678, "K", "Juan", "Modificado", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));
        UsuarioResponseDTO mockResponse = new UsuarioResponseDTO(id, 12345678, "K", "Juan", "Modificado", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));

        Mockito.when(usuarioService.actualizar(eq(id), any(UsuarioRequestDTO.class))).thenReturn(Optional.of(mockResponse));

        mockMvc.perform(put("/api/bibliotecaam/usuario/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(id))
                .andExpect(jsonPath("$.snombreUsu").value("Modificado"));
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: DELETE /api/bibliotecaam/usuario/{id} THEN: Retorna 24 No Content")
    void shouldDeleteUsuario() throws Exception {
        Long id = 1L;
        UsuarioResponseDTO mockResponse = new UsuarioResponseDTO(id, 12345678, "K", "Juan", null, "Pérez", "Gómez", LocalDate.now());

        Mockito.when(usuarioService.buscarPorId(id)).thenReturn(Optional.of(mockResponse));
        Mockito.doNothing().when(usuarioService).eliminar(id);

        mockMvc.perform(delete("/api/bibliotecaam/usuario/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: DELETE /api/bibliotecaam/usuario/{id} THEN: Retorna 404 Not Found")
    void shouldReturnNotFoundWhenDeletingNonExistentUsuario() throws Exception {
        Long id = 99L;
        Mockito.when(usuarioService.buscarPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/bibliotecaam/usuario/{id}", id))
                .andExpect(status().isNotFound());
    }
}