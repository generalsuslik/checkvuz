package checkvuz.checkvuz.university.department.service;

import checkvuz.checkvuz.university.department.entity.Department;
import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import checkvuz.checkvuz.university.program.entity.Program;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface DepartmentServiceInterface {

    List<Department> getDepartments();

    Department createDepartment(Department departmentToCreate);

    Department getDepartment(Long departmentId);

    Department updateDepartment(Department departmentToUpdate, Long departmentId);

    void deleteDepartment(Long departmentId);

    EntityModel<Department> convertDepartmentToModel(Department department);

    List<Program> getPrograms(Long departmentId);

    List<EntityModel<Program>> getProgramModels(Long departmentId);

    Department addProgram(Long departmentId, Long programId);

    Department removeProgram(Long departmentId, Long programId);

    List<DepartmentTag> getAssignedTags(Long departmentId);

    List<EntityModel<DepartmentTag>> getAssignedTagsModels(Long departmentId);

    Department assignTag(Long departmentId, Long departmentTagId);

    Department removeTag(Long departmentId, Long departmentTagId);
}
