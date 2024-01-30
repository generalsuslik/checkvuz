package checkvuz.checkvuz.faculty.controller;

import checkvuz.checkvuz.faculty.entity.FacultyTag;
import checkvuz.checkvuz.faculty.service.FacultyTagService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FacultyTagController {

    private final FacultyTagService facultyTagService;

    @GetMapping("/faculty-tags")
    public CollectionModel<EntityModel<FacultyTag>> getFacultyTags() {
        return facultyTagService.getFacultyTags();
    }

    @GetMapping("/faculty-tags/{facultyTagId}")
    public EntityModel<FacultyTag> getFacultyTag(@PathVariable Long facultyTagId) {
        return facultyTagService.getFacultyTag(facultyTagId);
    }
}
