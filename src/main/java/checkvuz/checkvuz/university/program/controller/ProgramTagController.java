package checkvuz.checkvuz.university.program.controller;

import checkvuz.checkvuz.university.program.entity.ProgramTag;
import checkvuz.checkvuz.university.program.service.ProgramTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/program-tags")
public class ProgramTagController {

    private final ProgramTagService programTagService;

    @GetMapping("")
    public CollectionModel<EntityModel<ProgramTag>> getProgramTags() {

        List<ProgramTag> tags = programTagService.getProgramTags();
        List<EntityModel<ProgramTag>> programTags = tags.stream()
                .map(programTagService::convertProgramTagToModel)
                .collect(Collectors.toList());

        return CollectionModel.of(programTags,
                linkTo(methodOn(ProgramTagController.class).getProgramTags()).withSelfRel()
        );
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<ProgramTag>> createProgramTag(@RequestBody ProgramTag programTagToCreate) {

        EntityModel<ProgramTag> entityModel = programTagService.convertProgramTagToModel(
                programTagService.createProgramTag(programTagToCreate)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/{programTagId}")
    public EntityModel<ProgramTag> getProgramTag(@PathVariable Long programTagId) {

        return programTagService.convertProgramTagToModel(
                programTagService.getProgramTag(programTagId)
        );
    }

    @PutMapping("/{programTagId}")
    public ResponseEntity<EntityModel<ProgramTag>> updateProgramTag(@RequestBody ProgramTag programTagToUpdate,
                                                                    @PathVariable Long programTagId) {

        EntityModel<ProgramTag> entityModel = programTagService.convertProgramTagToModel(
                programTagService.updateProgramTag(programTagToUpdate, programTagId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{programTagId}")
    public ResponseEntity<?> deleteProgramTag(@PathVariable Long programTagId) {

        programTagService.deleteProgramTag(programTagId);

        return ResponseEntity.noContent().build();
    }
}
