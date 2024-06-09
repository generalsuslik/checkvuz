package checkvuz.checkvuz.university.university.controller;

import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.program.controller.ProgramController;
import checkvuz.checkvuz.university.program.entity.Program;
import checkvuz.checkvuz.university.university.entity.University;
import checkvuz.checkvuz.university.university.entity.UniversityTag;
import checkvuz.checkvuz.university.university.service.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/universities")
@AllArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("")
    @Operation(
            summary = "Return a list of universities"
    )
    public CollectionModel<EntityModel<University>> getUniversities() {

        List<University> universities = universityService.getUniversities();

        List<EntityModel<University>> entityModels = universities.stream()
                .map(universityService::convertUniversityToModel)
                .toList();

        return CollectionModel.of(entityModels,
                linkTo(methodOn(UniversityController.class).getUniversities()).withSelfRel());
    }

    @PostMapping("")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Creates a university",
            description = "Creates a university with specified in universityToCreate data and an image (can be null)"
    )
    public ResponseEntity<?> createUniversity(
            @RequestBody @Parameter(description = "json university representation") University universityToCreate,
            @RequestParam("image") @Nullable MultipartFile imageFile) throws IOException {

        EntityModel<University> entityModel = universityService.convertUniversityToModel(
                universityService.createUniversity(universityToCreate, imageFile)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/{universityId}")
    @Operation(
            summary = "Returns university",
            description = "Returns a university with id = universityId"
    )
    public EntityModel<University> getUniversity(@PathVariable Long universityId) {

        return universityService.convertUniversityToModel(
                universityService.getUniversity(universityId)
        );
    }

    @PutMapping("/{universityId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Updates university",
            description = "Replaces university's (with universityId) data with data in universityToUpdate"
    )
    public ResponseEntity<?> updateUniversity(@RequestBody University universityToUpdate,
                                              @PathVariable @Parameter(description = "university to be updated 's id")
                                              Long universityId) {

        EntityModel<University> entityModel = universityService.convertUniversityToModel(
                universityService.updateUniversity(universityToUpdate, universityId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{universityId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Deletes university",
            description = "Deletes university with id = universityId"
    )
    public ResponseEntity<?> deleteUniversity(@PathVariable Long universityId) {

        universityService.deleteUniversity(universityId);
        return ResponseEntity.noContent().build();
    }

    // UNIVERSITY TAGS SECTION
    @GetMapping("/{universityId}/tags")
    @Operation(
            summary = "Returns assigned to university tags"
    )
    public CollectionModel<EntityModel<UniversityTag>> getAssignedTags(@PathVariable Long universityId) {

        List<EntityModel<UniversityTag>> assignedTags = universityService.getAssignedEntityTags(universityId);

        return CollectionModel.of(assignedTags,
                linkTo(methodOn(UniversityController.class).getAssignedTags(universityId)).withSelfRel());
    }

    @PutMapping("/{universityId}/tags/{tagId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Assigns tag to university",
            description = "Assigns tag with tagId to university with id = universityId"
    )
    public ResponseEntity<?> assignTag(@PathVariable Long universityId, @PathVariable Long tagId) {

        University university = universityService.assignTag(universityId, tagId);
        EntityModel<University> entityModel = universityService.convertUniversityToModel(university);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{universityId}/tags/{tagId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Removes tag",
            description = "Removes tag with id = tagId from uni with id = universityId"
    )
    public ResponseEntity<?> removeTag(@PathVariable Long universityId, @PathVariable Long tagId) {

        University university = universityService.removeTag(universityId, tagId);
        EntityModel<University> entityModel = universityService.convertUniversityToModel(university);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // UNIVERSITY IMAGES SECTION
    @PatchMapping("/{universityId}/image")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Adds image to university"
    )
    public ResponseEntity<?> addUniversityImage(
            @PathVariable Long universityId,
            @RequestParam("university_image") MultipartFile imageFile) throws IOException {

        EntityModel<University> entityModel = universityService.convertUniversityToModel(
                universityService.addUniversityImage(universityId, imageFile)
        );

        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }

    // UNIVERSITY FACULTIES SECTION
    @GetMapping("/{universityId}/faculties")
    @Operation(
            summary = "Returns a list of university's faculties",
            description = "Returns a list of university with universityId 's faculties"
    )
    public CollectionModel<EntityModel<Faculty>> getUniversityFaculties(@PathVariable Long universityId) {

        List<EntityModel<Faculty>> faculties = universityService.getUniversityFacultyModels(universityId);

        return CollectionModel.of(faculties,
                linkTo(methodOn(UniversityController.class).getUniversityFaculties(universityId)).withRel("faculties"));
    }

    @PostMapping("/{universityId}/faculties")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Creates a faculty in university"
    )
    public ResponseEntity<EntityModel<University>> createAndAssignFaculty(@PathVariable Long universityId,
                                                                       @RequestBody Faculty facultyToCreate) {


        EntityModel<University> university = universityService.convertUniversityToModel(
                universityService.createAndAssignFaculty(universityId, facultyToCreate)
        );

        return ResponseEntity.ok(university);
    }

    // UNIVERSITY STUDY PROGRAMS SECTION
    @GetMapping("/{universityId}/programs")
    @Operation(
            summary = "Returns a list of study programs",
            description = "Returns a list of university with universityId 's study programs"
    )
    public CollectionModel<EntityModel<Program>> getPrograms(@PathVariable Long universityId) {

        List<EntityModel<Program>> programs = universityService.getProgramModels(universityId);
        return CollectionModel.of(programs,
                linkTo(methodOn(ProgramController.class).getPrograms()).withRel("programs")
        );
    }

    @PutMapping("/{universityId}/programs/{programId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Adds a study program to university",
            description = """
                    Ads a study program exmp: `01.03.02 Прикладная математика и информатика` with id = programId
                    to university with id = universityId"""
    )
    public ResponseEntity<EntityModel<University>> addProgram(@PathVariable Long universityId,
                                                              @PathVariable Long programId) {

        EntityModel<University> university = universityService.convertUniversityToModel(
                universityService.addProgram(universityId, programId)
        );

        return ResponseEntity.ok(university);
    }

    @DeleteMapping("/{universityId}/programs/{programId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Removes program from university",
            description = "Removes study program with id = programId from university with id = universityId"
    )
    public ResponseEntity<EntityModel<University>> removeProgram(@PathVariable Long universityId,
                                                                 @PathVariable Long programId) {

        EntityModel<University> university = universityService.convertUniversityToModel(
                universityService.removeProgram(universityId, programId)
        );

        return ResponseEntity.ok(university);
    }
}
