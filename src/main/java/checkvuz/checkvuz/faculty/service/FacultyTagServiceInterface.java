package checkvuz.checkvuz.faculty.service;

import checkvuz.checkvuz.faculty.entity.FacultyTag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface FacultyTagServiceInterface {
    CollectionModel<EntityModel<FacultyTag>> getFacultyTags();
    ResponseEntity<?> createFacultyTag(FacultyTag facultyTagToCreate);
    EntityModel<FacultyTag> getFacultyTag(Long facultyTagId);
    ResponseEntity<?> updateFacultyTag(FacultyTag facultyTagToUpdate, Long facultyTagId);
    ResponseEntity<?> deleteFacultyTag(Long facultyTagId);
}
