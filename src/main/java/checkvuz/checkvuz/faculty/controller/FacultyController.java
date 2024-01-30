package checkvuz.checkvuz.faculty.controller;

import checkvuz.checkvuz.faculty.entity.Faculty;
import checkvuz.checkvuz.faculty.service.FacultyService;
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
public class FacultyController {

    private FacultyService facultyService;

    @GetMapping("faculties")
    public CollectionModel<EntityModel<Faculty>> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("faculties/{facultyId}")
    public EntityModel<Faculty> getFaculty(@PathVariable Long facultyId) {
        return facultyService.getFaculty(facultyId);
    }
}
