package checkvuz.checkvuz.faculty.service;

import checkvuz.checkvuz.faculty.entity.Faculty;
import checkvuz.checkvuz.faculty.entity.FacultyTag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface FacultyServiceInterface {
    CollectionModel<EntityModel<Faculty>> getAllFaculties();

    EntityModel<Faculty> getFaculty(Long facultyId);

    ResponseEntity<?> updateFaculty(Faculty facultyToUpdate, Long facultyId);

    ResponseEntity<?> deleteFaculty(Long facultyId);

    CollectionModel<EntityModel<FacultyTag>> getAssignedTags(Long facultyId);

    ResponseEntity<?> assignTag(Long facultyId, Long facultyTagId);

    ResponseEntity<?> removeTag(Long facultyId, Long facultyTagId);
}
