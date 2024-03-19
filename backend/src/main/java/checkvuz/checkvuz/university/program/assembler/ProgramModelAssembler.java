package checkvuz.checkvuz.university.program.assembler;

import checkvuz.checkvuz.university.program.controller.ProgramController;
import checkvuz.checkvuz.university.program.entity.Program;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProgramModelAssembler implements RepresentationModelAssembler<Program, EntityModel<Program>> {
    @Override
    @NonNull
    public EntityModel<Program> toModel(@NonNull Program program) {

        return EntityModel.of(program,
                linkTo(methodOn(ProgramController.class).getProgramById(program.getId())).withSelfRel(),
                linkTo(methodOn(ProgramController.class).getPrograms()).withRel("programs")
        );
    }
}
