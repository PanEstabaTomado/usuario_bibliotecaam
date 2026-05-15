package dfy1103.maq.usuario.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long idUsuario;

    private Integer numrunUsu;

    private String dvrunUsu;

    private String pnombreUsu;

    private String snombreUsu;

    private String appaternoUsu;

    private String apmaternoUsu;

    private LocalDate fechaNacUsu;
}
