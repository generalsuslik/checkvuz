package checkvuz.checkvuz.faculty.controller;

import checkvuz.checkvuz.faculty.entity.FacultyTag;
import checkvuz.checkvuz.faculty.service.FacultyTagService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FacultyTagController {

    private final FacultyTagService facultyTagService;

    @GetMapping("/faculty-tags")
    public CollectionModel<EntityModel<FacultyTag>> getFacultyTags() {
        return facultyTagService.getFacultyTags();
    }

    @PostMapping("faculty-tags")
    public ResponseEntity<?> createFacultyTag(@RequestBody FacultyTag facultyTagToCreate) {
        return facultyTagService.createFacultyTag(facultyTagToCreate);
    }

    @GetMapping("/faculty-tags/{facultyTagId}")
    public EntityModel<FacultyTag> getFacultyTag(@PathVariable Long facultyTagId) {
        return facultyTagService.getFacultyTag(facultyTagId);
    }

    @PutMapping("/faculty-tags/{facultyTagId}")
    public ResponseEntity<?> updateFacultyTag(@RequestBody FacultyTag facultyTag,
                                              @PathVariable Long facultyTagId) {

        return facultyTagService.updateFacultyTag(facultyTag, facultyTagId);
    }

    @DeleteMapping("/faculty-tags/{facultyTagId}")
    public ResponseEntity<?> deleteFacultyTag(@PathVariable Long facultyTagId) {
        return facultyTagService.deleteFacultyTag(facultyTagId);
    }
}
