package checkvuz.checkvuz.faculty.service;

import checkvuz.checkvuz.faculty.entity.Faculty;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface FacultyServiceInterface {
    CollectionModel<EntityModel<Faculty>> getAllFaculties();
    ResponseEntity<?> createFaculty(Faculty facultyToCreate);
    EntityModel<Faculty> getFaculty(Long facultyId);
    ResponseEntity<?> updateFaculty(Faculty facultyToUpdate, Long facultyId);
    ResponseEntity<?> deleteFaculty(Long facultyId);
}
