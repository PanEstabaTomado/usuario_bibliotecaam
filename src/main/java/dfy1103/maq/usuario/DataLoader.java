package dfy1103.maq.usuario;

import dfy1103.maq.usuario.model.Usuario;
import dfy1103.maq.usuario.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();

        for (int i = 0; i < 6; i++) {
            Usuario usuario = new Usuario();
            usuario.setNumrunUsu(faker.number().numberBetween(10000000,99999999));
            String opcionesDv = "0123456789K";
            int indice = faker.number().numberBetween(0, opcionesDv.length());
            String dvAleatorio = String.valueOf(opcionesDv.charAt(indice));
            usuario.setDvrunUsu(dvAleatorio);
            usuario.setPnombreUsu(faker.name().firstName());
            usuario.setSnombreUsu(faker.name().firstName());
            usuario.setAppaternoUsu(faker.name().lastName());
            usuario.setApmaternoUsu(faker.name().lastName());
            usuario.setFechaNacUsu((faker.timeAndDate().birthday()));

            usuarioRepository.save(usuario);
        }
    }
}
