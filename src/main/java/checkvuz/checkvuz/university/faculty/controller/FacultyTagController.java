package checkvuz.checkvuz.university.faculty.controller;

import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import checkvuz.checkvuz.university.faculty.service.FacultyTagService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/faculty-tags")
@AllArgsConstructor
public class FacultyTagController {

    private final FacultyTagService facultyTagService;

    @GetMapping("")
    public CollectionModel<EntityModel<FacultyTag>> getFacultyTags() {
        return facultyTagService.getFacultyTags();
    }

    @PostMapping("")
    public ResponseEntity<?> createFacultyTag(@RequestBody FacultyTag facultyTagToCreate) {
        return facultyTagService.createFacultyTag(facultyTagToCreate);
    }

    @GetMapping("/{facultyTagId}")
    public EntityModel<FacultyTag> getFacultyTag(@PathVariable Long facultyTagId) {
        return facultyTagService.getFacultyTag(facultyTagId);
    }

    @PutMapping("/{facultyTagId}")
    public ResponseEntity<?> updateFacultyTag(@RequestBody FacultyTag facultyTag,
                                              @PathVariable Long facultyTagId) {

        return facultyTagService.updateFacultyTag(facultyTag, facultyTagId);
    }

    @DeleteMapping("/{facultyTagId}")
    public ResponseEntity<?> deleteFacultyTag(@PathVariable Long facultyTagId) {
        return facultyTagService.deleteFacultyTag(facultyTagId);
    }
}
