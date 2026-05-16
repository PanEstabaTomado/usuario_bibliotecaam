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


        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        usuarioRepository.save(new Usuario(null, 12345678, "K", "Jose","Jesus","Pizarro","Navarro", LocalDate.of(1986,7,13)));
        log.info(">>> DataInitializer: {} usuario insertados correctamente.",
                usuarioRepository.count());
    }
}
