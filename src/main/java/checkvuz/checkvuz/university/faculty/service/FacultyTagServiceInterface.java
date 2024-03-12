package checkvuz.checkvuz.university.faculty.service;

import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface FacultyTagServiceInterface {
    List<FacultyTag> getFacultyTags();
    FacultyTag createFacultyTag(FacultyTag facultyTagToCreate);
    FacultyTag getFacultyTag(Long facultyTagId);
    FacultyTag updateFacultyTag(FacultyTag facultyTagToUpdate, Long facultyTagId);
    void deleteFacultyTag(Long facultyTagId);
    EntityModel<FacultyTag> convertFacultyTagToModel(FacultyTag facultyTag);
}
