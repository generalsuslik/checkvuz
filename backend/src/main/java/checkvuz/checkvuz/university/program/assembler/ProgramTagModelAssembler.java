package checkvuz.checkvuz.university.program.assembler;

import checkvuz.checkvuz.university.program.controller.ProgramTagController;
import checkvuz.checkvuz.university.program.entity.ProgramTag;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProgramTagModelAssembler implements RepresentationModelAssembler<ProgramTag, EntityModel<ProgramTag>> {
    @Override
    @NonNull
    public EntityModel<ProgramTag> toModel(@NonNull ProgramTag programTag) {

        return EntityModel.of(programTag,
                linkTo(methodOn(ProgramTagController.class).getProgramTag(programTag.getId())).withSelfRel(),
                linkTo(methodOn(ProgramTagController.class).getProgramTags()).withRel("program_tags")
        );
    }
}
