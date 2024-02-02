package checkvuz.checkvuz.department.service;

import checkvuz.checkvuz.department.entity.Department;
import checkvuz.checkvuz.department.entity.DepartmentTag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface DepartmentServiceInterface {

    CollectionModel<EntityModel<Department>> getDepartments();

    EntityModel<Department> getDepartment(Long departmentId);

    ResponseEntity<EntityModel<Department>> updateDepartment(Department departmentToUpdate, Long departmentId);

    ResponseEntity<?> deleteDepartment(Long departmentId);

    CollectionModel<EntityModel<DepartmentTag>> getAssignedTags(Long departmentId);

    ResponseEntity<EntityModel<Department>> assignTag(Long departmentId, Long departmentTagId);

    ResponseEntity<EntityModel<Department>> removeTag(Long departmentId, Long departmentTagId);
}
