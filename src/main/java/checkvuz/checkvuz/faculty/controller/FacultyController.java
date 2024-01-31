package checkvuz.checkvuz.faculty.controller;

import checkvuz.checkvuz.faculty.entity.Faculty;
import checkvuz.checkvuz.faculty.entity.FacultyTag;
import checkvuz.checkvuz.faculty.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FacultyController {

    private FacultyService facultyService;


    // MAIN FACULTY SECTION
    @GetMapping("faculties")
    public CollectionModel<EntityModel<Faculty>> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("faculties/{facultyId}")
    public EntityModel<Faculty> getFaculty(@PathVariable Long facultyId) {
        return facultyService.getFaculty(facultyId);
    }

    @PutMapping("/faculties/{facultyId}")
    public ResponseEntity<?> updateFaculty(@RequestBody Faculty facultyToUpdate, @PathVariable Long facultyId) {
        return facultyService.updateFaculty(facultyToUpdate, facultyId);
    }

    @DeleteMapping("/faculties/{facultyId}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long facultyId) {
        return facultyService.deleteFaculty(facultyId);
    }

    // FACULTY TAGS SECTION
    @GetMapping("/faculties/{facultyId}/tags")
    public CollectionModel<EntityModel<FacultyTag>> getAssignedTags(@PathVariable Long facultyId) {
        return facultyService.getAssignedTags(facultyId);
    }

    @PutMapping("/faculties/{facultyId}/tags/{facultyTagId}")
    public ResponseEntity<?> assignTag(@PathVariable Long facultyId, @PathVariable Long facultyTagId) {
        return facultyService.assignTag(facultyId, facultyTagId);
    }

    @DeleteMapping("/faculties/{facultyId}/tags/{facultyTagId}")
    public ResponseEntity<?> removeTag(@PathVariable Long facultyId, @PathVariable Long facultyTagId) {
        return facultyService.removeTag(facultyId, facultyTagId);
    }
}
