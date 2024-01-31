package checkvuz.checkvuz.university.controller;

import checkvuz.checkvuz.faculty.entity.Faculty;
import checkvuz.checkvuz.university.entity.University;
import checkvuz.checkvuz.university.entity.UniversityTag;
import checkvuz.checkvuz.university.service.UniversityService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("/universities")
    public CollectionModel<EntityModel<University>> getUniversities() {
        return universityService.getUniversities();
    }

    @PostMapping("/universities")
    public ResponseEntity<?> createUniversity(@RequestBody University universityToCreate) {
        return universityService.createUniversity(universityToCreate);
    }

    @GetMapping("/universities/{universityId}")
    public EntityModel<University> getUniversity(@PathVariable Long universityId) {
        return universityService.getUniversity(universityId);
    }

    @PutMapping("/universities/{universityId}")
    public ResponseEntity<?> updateUniversity(@RequestBody University universityToUpdate,
                                              @PathVariable Long universityId) {

        return universityService.updateUniversity(universityToUpdate, universityId);
    }

    @DeleteMapping("/universities/{universityId}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Long universityId) {
        return universityService.deleteUniversity(universityId);
    }

    // UNIVERSITY TAGS SECTION
    @GetMapping("/universities/{universityId}/tags")
    public CollectionModel<EntityModel<UniversityTag>> getAssignedTags(@PathVariable Long universityId) {
        return universityService.getAssignedTags(universityId);
    }

    @PutMapping("/universities/{universityId}/tags/{tagId}")
    public ResponseEntity<?> assignTag(@PathVariable Long universityId, @PathVariable Long tagId) {
        return universityService.assignTag(universityId, tagId);
    }

    @DeleteMapping("/universities/{universityId}/tags/{tagId}")
    public ResponseEntity<?> removeTag(@PathVariable Long universityId, @PathVariable Long tagId) {
        return universityService.removeTag(universityId, tagId);
    }

    // UNIVERSITY FACULTIES SECTION
    @GetMapping("/universities/{universityId}/faculties")
    public CollectionModel<EntityModel<Faculty>> getUniversityFaculties(@PathVariable Long universityId) {
        return universityService.getUniversityFaculties(universityId);
    }

    @PostMapping("/universities/{universityId}/faculties")
    public ResponseEntity<EntityModel<Faculty>> createAndAssignFaculty(@PathVariable Long universityId,
                                                                       @RequestBody Faculty facultyToCreate) {

        return universityService.createAndAssignFaculty(universityId, facultyToCreate);
    }
}
