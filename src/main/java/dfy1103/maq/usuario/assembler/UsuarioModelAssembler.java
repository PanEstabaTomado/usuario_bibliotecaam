package dfy1103.maq.usuario.assembler;

import dfy1103.maq.usuario.controller.UsuarioController;
import dfy1103.maq.usuario.dto.UsuarioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioResponseDTO, EntityModel<UsuarioResponseDTO>> {
    @Override
    public EntityModel<UsuarioResponseDTO> toModel(UsuarioResponseDTO usuarioDto){
        return EntityModel.of(usuarioDto,
                linkTo(methodOn(UsuarioController.class).obtenerPorId(usuarioDto.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).obtenerTodas()).withRel("usuarios"));
    }
}
