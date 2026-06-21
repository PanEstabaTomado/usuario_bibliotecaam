package dfy1103.maq.usuario;

import dfy1103.maq.usuario.dto.UsuarioRequestDTO;
import dfy1103.maq.usuario.dto.UsuarioResponseDTO;
import dfy1103.maq.usuario.model.Usuario;
import dfy1103.maq.usuario.repository.UsuarioRepository;
import dfy1103.maq.usuario.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = UsuarioService.class)
@ActiveProfiles("test")
@DisplayName("Tests Unitarios - UsuarioService")
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("GIVEN: Existen usuarios WHEN: obtenerTodos THEN: Retorna la lista de DTOs")
    void shouldReturnAllUsuarios() {
        List<Usuario> mockList = Arrays.asList(
                new Usuario(1L, 12345678, "K", "Juan", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10)),
                new Usuario(2L, 87654321, "0", "María", "Elena", "López", "Silva", LocalDate.of(1995, 8, 25))
        );
        Mockito.when(usuarioRepository.findAll()).thenReturn(mockList);

        List<UsuarioResponseDTO> resultado = usuarioService.obtenerTodos();

        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getIdUsuario());
        assertEquals("Juan", resultado.get(0).getPnombreUsu());
    }

    @Test
    @DisplayName("GIVEN: Existe usuario con segundo nombre WHEN: buscarPorId THEN: Retorna el DTO con su segundo nombre original")
    void shouldReturnUsuarioByIdWithSecondName() {
        Long id = 1L;
        Usuario usuario = new Usuario(id, 12345678, "K", "Juan", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Optional<UsuarioResponseDTO> resultado = usuarioService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getSnombreUsu());
    }

    @Test
    @DisplayName("GIVEN: Existe usuario SIN segundo nombre WHEN: buscarPorId THEN: El DTO contiene el mensaje por defecto")
    void shouldReturnUsuarioByIdWithoutSecondName() {
        Long id = 1L;
        // snombreUsu se envía como null
        Usuario usuario = new Usuario(id, 12345678, "K", "Juan", null, "Pérez", "Gómez", LocalDate.of(1990, 5, 10));
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Optional<UsuarioResponseDTO> resultado = usuarioService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals("No tiene segundo nombre.", resultado.get().getSnombreUsu());
    }

    @Test
    @DisplayName("GIVEN: Request válido WHEN: guardar THEN: Registra al usuario y retorna el DTO")
    void shouldSaveUsuarioSuccessfully() {
        UsuarioRequestDTO request = new UsuarioRequestDTO(12345678, "K", "Juan", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));
        Usuario usuarioGuardado = new Usuario(100L, 12345678, "K", "Juan", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));

        Mockito.when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        UsuarioResponseDTO resultado = usuarioService.guardar(request);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getIdUsuario());
        assertEquals("Juan", resultado.getPnombreUsu());
    }

    @Test
    @DisplayName("GIVEN: ID y Request válido WHEN: actualizar THEN: Modifica los datos del usuario existente")
    void shouldUpdateUsuarioSuccessfully() {
        Long id = 1L;
        Usuario existente = new Usuario(id, 12345678, "K", "Juan", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));
        UsuarioRequestDTO request = new UsuarioRequestDTO(12345678, "K", "Andrés", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));
        Usuario modificado = new Usuario(id, 12345678, "K", "Andrés", "Carlos", "Pérez", "Gómez", LocalDate.of(1990, 5, 10));

        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(existente));
        Mockito.when(usuarioRepository.save(any(Usuario.class))).thenReturn(modificado);

        Optional<UsuarioResponseDTO> resultado = usuarioService.actualizar(id, request);

        assertTrue(resultado.isPresent());
        assertEquals("Andrés", resultado.get().getPnombreUsu());
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: eliminar THEN: Invoca la remoción en el repositorio")
    void shouldDeleteUsuario() {
        Long id = 1L;
        Mockito.doNothing().when(usuarioRepository).deleteById(id);

        assertDoesNotThrow(() -> usuarioService.eliminar(id));
        Mockito.verify(usuarioRepository, Mockito.times(1)).deleteById(id);
    }
}