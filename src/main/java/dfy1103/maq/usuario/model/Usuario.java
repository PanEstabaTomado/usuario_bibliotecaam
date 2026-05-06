package dfy1103.maq.usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,precision = 8)
    private Integer numrunUsu;

    @Column(nullable = false,length = 1)
    private String dvrunUsu;

    @Column(nullable = false,length = 30)
    private String pnombreUsu;

    @Column(nullable = true,length = 30)
    private String snombreUsu;

    @Column(nullable = false,length = 30)
    private String appaternoUsu;

    @Column(nullable = false,length = 30)
    private String apmaternoUsu;

    @Column(nullable = false)
    private LocalDate fechaNacUsu;
}