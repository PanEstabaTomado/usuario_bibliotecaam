package dfy1103.maq.usuario.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {


    @Positive(message = "El numero de rut no puede ser 0 o negativo.")
    @NotNull(message = "El numero de rut no puede estar vacio.")
    private Integer numrunUsu;

    @NotBlank(message = "El Digito Verificador no puede estar vacio.")
    private String dvrunUsu;

    @NotBlank(message = "El primer nombre no puede estar vacio.")
    private String pnombreUsu;

    private String snombreUsu;

    @NotBlank(message = "El apellido paterno no puede estar vacio.")
    private String appaternoUsu;

    @NotBlank(message = "El apellido materno no puede estar vacio.")
    private String apmaternoUsu;

    @NotNull(message = "La fecha de nacimiento no puede estar vacia.")
    private LocalDate fechaNacUsu;
}
