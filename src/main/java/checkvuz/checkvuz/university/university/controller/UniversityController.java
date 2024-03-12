package checkvuz.checkvuz.university.university.controller;

import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.university.entity.University;
import checkvuz.checkvuz.university.university.entity.UniversityTag;
import checkvuz.checkvuz.university.university.service.UniversityService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/universities")
@AllArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("")
    public CollectionModel<EntityModel<University>> getUniversities() {

        List<University> universities = universityService.getUniversities();

        List<EntityModel<University>> entityModels = universities.stream()
                .map(universityService::convertUniversityToModel)
                .toList();

        return CollectionModel.of(entityModels,
                linkTo(methodOn(UniversityController.class).getUniversities()).withSelfRel());
    }

    @PostMapping("")
    public ResponseEntity<?> createUniversity(@RequestBody University universityToCreate) {

        EntityModel<University> entityModel = universityService.convertUniversityToModel(
                universityService.createUniversity(universityToCreate)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/{universityId}")
    public EntityModel<University> getUniversity(@PathVariable Long universityId) {

        return universityService.convertUniversityToModel(
                universityService.getUniversity(universityId)
        );
    }

    @PutMapping("/{universityId}")
    public ResponseEntity<?> updateUniversity(@RequestBody University universityToUpdate,
                                              @PathVariable Long universityId) {

        EntityModel<University> entityModel = universityService.convertUniversityToModel(
                universityService.updateUniversity(universityToUpdate, universityId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{universityId}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Long universityId) {

        universityService.deleteUniversity(universityId);
        return ResponseEntity.noContent().build();
    }

    // UNIVERSITY TAGS SECTION
    @GetMapping("/{universityId}/tags")
    public CollectionModel<EntityModel<UniversityTag>> getAssignedTags(@PathVariable Long universityId) {

        List<EntityModel<UniversityTag>> assignedTags = universityService.getAssignedEntityTags(universityId);

        return CollectionModel.of(assignedTags,
                linkTo(methodOn(UniversityController.class).getAssignedTags(universityId)).withSelfRel());
    }

    @PutMapping("/{universityId}/tags/{tagId}")
    public ResponseEntity<?> assignTag(@PathVariable Long universityId, @PathVariable Long tagId) {

        University university = universityService.assignTag(universityId, tagId);
        EntityModel<University> entityModel = universityService.convertUniversityToModel(university);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{universityId}/tags/{tagId}")
    public ResponseEntity<?> removeTag(@PathVariable Long universityId, @PathVariable Long tagId) {

        University university = universityService.removeTag(universityId, tagId);
        EntityModel<University> entityModel = universityService.convertUniversityToModel(university);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // UNIVERSITY FACULTIES SECTION
    @GetMapping("/{universityId}/faculties")
    public CollectionModel<EntityModel<Faculty>> getUniversityFaculties(@PathVariable Long universityId) {
        return universityService.getUniversityFaculties(universityId);
    }

    @PostMapping("/{universityId}/faculties")
    public ResponseEntity<EntityModel<Faculty>> createAndAssignFaculty(@PathVariable Long universityId,
                                                                       @RequestBody Faculty facultyToCreate) {

        return universityService.createAndAssignFaculty(universityId, facultyToCreate);
    }
}
