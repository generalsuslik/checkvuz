package checkvuz.checkvuz.university.faculty.service;

import checkvuz.checkvuz.university.department.entity.Department;
import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface FacultyServiceInterface {
    CollectionModel<EntityModel<Faculty>> getAllFaculties();

    EntityModel<Faculty> getFaculty(Long facultyId);

    ResponseEntity<?> updateFaculty(Faculty facultyToUpdate, Long facultyId);

    ResponseEntity<?> deleteFaculty(Long facultyId);

    CollectionModel<EntityModel<FacultyTag>> getAssignedTags(Long facultyId);

    ResponseEntity<EntityModel<Faculty>> assignTag(Long facultyId, Long facultyTagId);

    ResponseEntity<?> removeTag(Long facultyId, Long facultyTagId);

    CollectionModel<EntityModel<Department>> getAssignedDepartments(Long facultyId);

    ResponseEntity<EntityModel<Department>> createAndAssignDepartment(Department departmentToCreate, Long facultyId);
}
