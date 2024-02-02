package checkvuz.checkvuz.faculty.controller;

import checkvuz.checkvuz.department.entity.Department;
import checkvuz.checkvuz.faculty.entity.Faculty;
import checkvuz.checkvuz.faculty.entity.FacultyTag;
import checkvuz.checkvuz.faculty.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faculties")
@AllArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;


    // MAIN FACULTY SECTION
    @GetMapping("")
    public CollectionModel<EntityModel<Faculty>> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/{facultyId}")
    public EntityModel<Faculty> getFaculty(@PathVariable Long facultyId) {
        return facultyService.getFaculty(facultyId);
    }

    @PutMapping("/{facultyId}")
    public ResponseEntity<?> updateFaculty(@RequestBody Faculty facultyToUpdate, @PathVariable Long facultyId) {
        return facultyService.updateFaculty(facultyToUpdate, facultyId);
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long facultyId) {
        return facultyService.deleteFaculty(facultyId);
    }

    // FACULTY TAGS SECTION
    @GetMapping("/{facultyId}/tags")
    public CollectionModel<EntityModel<FacultyTag>> getAssignedTags(@PathVariable Long facultyId) {
        return facultyService.getAssignedTags(facultyId);
    }

    @PutMapping("/{facultyId}/tags/{facultyTagId}")
    public ResponseEntity<?> assignTag(@PathVariable Long facultyId, @PathVariable Long facultyTagId) {
        return facultyService.assignTag(facultyId, facultyTagId);
    }

    @DeleteMapping("/{facultyId}/tags/{facultyTagId}")
    public ResponseEntity<?> removeTag(@PathVariable Long facultyId, @PathVariable Long facultyTagId) {
        return facultyService.removeTag(facultyId, facultyTagId);
    }

    // FACULTY DEPARTMENTS SECTION
    @GetMapping("/{facultyTagId}/departments")
    public CollectionModel<EntityModel<Department>> getAssignedDepartments(@PathVariable Long facultyTagId) {
        return facultyService.getAssignedDepartments(facultyTagId);
    }

    @PostMapping("/{facultyId}/departments")
    public ResponseEntity<EntityModel<Department>> createAndAssignDepartment(@RequestBody Department departmentToCreate,
                                                                             @PathVariable Long facultyId) {

        return facultyService.createAndAssignDepartment(departmentToCreate, facultyId);
    }
}
