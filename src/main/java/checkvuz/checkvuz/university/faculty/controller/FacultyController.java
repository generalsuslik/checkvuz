package checkvuz.checkvuz.university.faculty.controller;

import checkvuz.checkvuz.university.department.entity.Department;
import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import checkvuz.checkvuz.university.faculty.service.FacultyService;
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
@RequestMapping("/faculties")
@AllArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;


    // MAIN FACULTY SECTION
    @GetMapping("")
    public CollectionModel<EntityModel<Faculty>> getAllFaculties() {

        List<Faculty> faculties = facultyService.getAllFaculties();
        List<EntityModel<Faculty>> facultyModels = faculties.stream()
                .map(facultyService::convertFacultyToModel)
                .collect(Collectors.toList());

        return CollectionModel.of(facultyModels,
                linkTo(methodOn(FacultyController.class).getAllFaculties()).withSelfRel());
    }

    @GetMapping("/{facultyId}")
    public EntityModel<Faculty> getFaculty(@PathVariable Long facultyId) {

        return facultyService.convertFacultyToModel(
                facultyService.getFaculty(facultyId)
        );
    }

    @PutMapping("/{facultyId}")
    public ResponseEntity<EntityModel<Faculty>> updateFaculty(@RequestBody Faculty facultyToUpdate,
                                                              @PathVariable Long facultyId) {

        EntityModel<Faculty> entityModel = facultyService.convertFacultyToModel(
                facultyService.updateFaculty(facultyToUpdate, facultyId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long facultyId) {

        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.noContent().build();
    }

    // FACULTY TAGS SECTION
    @GetMapping("/{facultyId}/tags")
    public CollectionModel<EntityModel<FacultyTag>> getAssignedTags(@PathVariable Long facultyId) {

        List<EntityModel<FacultyTag>> assignedTags = facultyService.getAssignedTagsModels(facultyId);
        return CollectionModel.of(assignedTags,
                linkTo(methodOn(FacultyTagController.class).getFacultyTags()).withSelfRel());
    }

    @PutMapping("/{facultyId}/tags/{facultyTagId}")
    public ResponseEntity<EntityModel<Faculty>> assignTag(@PathVariable Long facultyId,
                                                          @PathVariable Long facultyTagId) {

        EntityModel<Faculty> entityModel = facultyService.convertFacultyToModel(
                facultyService.assignTag(facultyId, facultyTagId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{facultyId}/tags/{facultyTagId}")
    public ResponseEntity<EntityModel<Faculty>> removeTag(@PathVariable Long facultyId,
                                                          @PathVariable Long facultyTagId) {

        EntityModel<Faculty> entityModel = facultyService.convertFacultyToModel(
                facultyService.removeTag(facultyId, facultyTagId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // FACULTY DEPARTMENTS SECTION
    @GetMapping("/{facultyTagId}/departments")
    public CollectionModel<EntityModel<Department>> getAssignedDepartments(@PathVariable Long facultyTagId) {

        List<EntityModel<Department>> departmentModels = facultyService.getAssignedDepartmentsModels(facultyTagId);

        return CollectionModel.of(departmentModels,
                linkTo(methodOn(FacultyController.class).getAssignedDepartments(facultyTagId)).withSelfRel());
    }

    @PostMapping("/{facultyId}/departments")
    public ResponseEntity<EntityModel<Department>> createAndAssignDepartment(@RequestBody Department departmentToCreate,
                                                                             @PathVariable Long facultyId) {

        EntityModel<Department> entityModel = facultyService.createAndAssignDepartment(departmentToCreate, facultyId);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
