package checkvuz.checkvuz.university.faculty.service;

import checkvuz.checkvuz.university.department.entity.Department;
import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import org.springframework.hateoas.EntityModel;

import java.util.List;


public interface FacultyServiceInterface {
    List<Faculty> getAllFaculties();

    Faculty getFaculty(Long facultyId);

    Faculty updateFaculty(Faculty facultyToUpdate, Long facultyId);

    void deleteFaculty(Long facultyId);

    EntityModel<Faculty> convertFacultyToModel(Faculty faculty);

    List<FacultyTag> getAssignedTags(Long facultyId);

    List<EntityModel<FacultyTag>> getAssignedTagsModels(Long facultyId);

    Faculty assignTag(Long facultyId, Long facultyTagId);

    Faculty removeTag(Long facultyId, Long facultyTagId);

    List<Department> getAssignedDepartments(Long facultyId);

    List<EntityModel<Department>> getAssignedDepartmentsModels(Long facultyId);

    EntityModel<Department> createAndAssignDepartment(Department departmentToCreate, Long facultyId);
}
