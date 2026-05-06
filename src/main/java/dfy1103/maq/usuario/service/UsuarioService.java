package dfy1103.maq.usuario.service;

import dfy1103.maq.usuario.dto.UsuarioRequestDTO;
import dfy1103.maq.usuario.dto.UsuarioResponseDTO;
import dfy1103.maq.usuario.model.Usuario;
import dfy1103.maq.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private UsuarioResponseDTO mapToDTO(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNumrunUsu(),
                usuario.getDvrunUsu(),
                usuario.getPnombreUsu(),
                usuario.getSnombreUsu(),
                usuario.getAppaternoUsu(),
                usuario.getApmaternoUsu(),
                usuario.getFechaNacUsu()
        );
    }

    public List<UsuarioResponseDTO> obtenerTodos(){
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> buscarPorId(Long id){
        return usuarioRepository.findById(id).map(this::mapToDTO);
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO doto){
         Usuario usuario = new Usuario(
                 null,
                 doto.getNumrunUsu(),
                 doto.getDvrunUsu(),
                 doto.getPnombreUsu(),
                 doto.getSnombreUsu(),
                 doto.getAppaternoUsu(),
                 doto.getApmaternoUsu(),
                 doto.getFechaNacUsu()
         );
         return mapToDTO((usuarioRepository.save(usuario)));
    }

    public Optional<UsuarioResponseDTO> actualizar(Long id, UsuarioRequestDTO doto){
        return usuarioRepository.findById(id).map(existente ->
        {
            existente.setNumrunUsu(doto.getNumrunUsu());
            existente.setDvrunUsu(doto.getDvrunUsu());
            existente.setPnombreUsu(doto.getPnombreUsu());
            existente.setSnombreUsu(doto.getSnombreUsu());
            existente.setAppaternoUsu(doto.getAppaternoUsu());
            existente.setApmaternoUsu(doto.getApmaternoUsu());
            existente.setFechaNacUsu(doto.getFechaNacUsu());
            return mapToDTO(usuarioRepository.save(existente));
        });
    }

    public void eliminar(Long id){
        usuarioRepository.deleteById(id);
    }


}