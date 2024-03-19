package checkvuz.checkvuz.university.program.controller;

import checkvuz.checkvuz.university.program.entity.Program;
import checkvuz.checkvuz.university.program.entity.ProgramTag;
import checkvuz.checkvuz.university.program.service.ProgramService;
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
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;

    @GetMapping("")
    public CollectionModel<EntityModel<Program>> getPrograms() {

        List<Program> programs = programService.getPrograms();

        List<EntityModel<Program>> programModels = programs.stream()
                .map(programService::convertProgramToModel)
                .collect(Collectors.toList());

        return CollectionModel.of(programModels,
                linkTo(methodOn(ProgramController.class).getPrograms()).withSelfRel());
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<Program>> createProgram(@RequestBody Program programToCreate) {

        EntityModel<Program> entityModel = programService.convertProgramToModel(
                programService.createProgram(programToCreate)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/{programId}")
    public EntityModel<Program> getProgramById(@PathVariable Long programId) {

        return programService.convertProgramToModel(
                programService.getProgramById(programId)
        );
    }

    @GetMapping("/title/{programTitle}")
    public EntityModel<Program> getProgramByTitle(@PathVariable String programTitle) {

        return programService.convertProgramToModel(
                programService.getProgramByTitle(programTitle)
        );
    }

    @PutMapping("/{programId}")
    public ResponseEntity<EntityModel<Program>> updateProgram(@RequestBody Program programToUpdate,
                                                              @PathVariable Long programId) {

        EntityModel<Program> entityModel = programService.convertProgramToModel(
                programService.updateProgram(programToUpdate, programId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<?> deleteProgram(@PathVariable Long programId) {

        programService.deleteProgram(programId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{programId}/tags")
    public CollectionModel<EntityModel<ProgramTag>> getAssignedTags(@PathVariable Long programId) {

        List<EntityModel<ProgramTag>> assignedTags = programService.getAssignedTagModels(programId);
        return CollectionModel.of(assignedTags,
                linkTo(methodOn(ProgramTagController.class).getProgramTags()).withRel("tags")
        );
    }

}
