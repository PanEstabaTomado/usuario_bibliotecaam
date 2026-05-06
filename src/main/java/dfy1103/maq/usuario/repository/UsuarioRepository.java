package dfy1103.maq.usuario.repository;

import dfy1103.maq.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.appaternoUsu = :apellidoPat")
    List<Usuario> findByApellidoPaterno(@Param("apellidoPat") String apellidoPat);

    @Query("SELECT u FROM Usuario u WHERE u.apmaternoUsu = :apellidoMat")
    List<Usuario> findByApellidoMaterno(@Param("apellidoMat") String apellidoMat);


}