package dfy1103.maq.usuario.config;

import dfy1103.maq.usuario.model.Usuario;
import dfy1103.maq.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {

        // ── GUARDIA PRINCIPAL ────────────────────────
        // Si ya existen categorías en la BD, no hacemos nada.
        // Esto evita duplicar datos en cada reinicio del servidor.
        // Si un estudiante borra una categoría desde phpMyAdmin
        // pero quedan otras, el inicializador NO actúa.
        // Para "resetear", vaciar la BD manualmente y reiniciar.

        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        // ── CATEGORÍAS ───────────────────────────────
        // save() devuelve el objeto con el id asignado por MySQL.
        // Guardamos la referencia para usarla en los libros.

        // ── LIBROS ───────────────────────────────────
        // Asociamos cada libro a la categoría creada arriba.
        // El campo 'id' va null porque MySQL lo genera (AUTO_INCREMENT).
        usuarioRepository.save(new Usuario(null, 12345678, "K", "Jose","Jesus","Pizarro","Navarro", LocalDate.of(1986,7,13)));
        log.info(">>> DataInitializer: {} libros insertados correctamente.",
                usuarioRepository.count());
    }
}
