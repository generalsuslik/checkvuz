package checkvuz.checkvuz.university.faculty.controller;

import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import checkvuz.checkvuz.university.faculty.service.FacultyTagService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/faculty-tags")
@AllArgsConstructor
public class FacultyTagController {

    private final FacultyTagService facultyTagService;

    @GetMapping("")
    public CollectionModel<EntityModel<FacultyTag>> getFacultyTags() {

        List<FacultyTag> facultyTags = facultyTagService.getFacultyTags();
        List<EntityModel<FacultyTag>> facultyTagModels = facultyTags.stream()
                .map(facultyTagService::convertFacultyTagToModel)
                .collect(Collectors.toList());

        return CollectionModel.of(facultyTagModels,
                linkTo(methodOn(FacultyTagController.class).getFacultyTags()).withSelfRel());
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<FacultyTag>> createFacultyTag(@RequestBody FacultyTag facultyTagToCreate) {

        EntityModel<FacultyTag> entityModel = facultyTagService.convertFacultyTagToModel(
                facultyTagService.createFacultyTag(facultyTagToCreate)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/{facultyTagId}")
    public EntityModel<FacultyTag> getFacultyTag(@PathVariable Long facultyTagId) {

        return facultyTagService.convertFacultyTagToModel(
                facultyTagService.getFacultyTag(facultyTagId)
        );
    }

    @PutMapping("/{facultyTagId}")
    public ResponseEntity<EntityModel<FacultyTag>> updateFacultyTag(@RequestBody FacultyTag facultyTag,
                                                                    @PathVariable Long facultyTagId) {

        EntityModel<FacultyTag> entityModel = facultyTagService.convertFacultyTagToModel(
                facultyTagService.updateFacultyTag(facultyTag, facultyTagId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{facultyTagId}")
    public ResponseEntity<?> deleteFacultyTag(@PathVariable Long facultyTagId) {

        facultyTagService.deleteFacultyTag(facultyTagId);
        return ResponseEntity.noContent().build();
    }
}
