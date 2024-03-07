package checkvuz.checkvuz.university.department.service;

import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface DepartmentTagServiceInterface {

    CollectionModel<EntityModel<DepartmentTag>> getDepartmentTags();

    ResponseEntity<EntityModel<DepartmentTag>> createDepartmentTag(DepartmentTag tagToCreate);

    EntityModel<DepartmentTag> getDepartmentTag(Long departmentTagId);

    ResponseEntity<EntityModel<DepartmentTag>> updateDepartmentTag(DepartmentTag departmentTagToUpdate,
                                                                   Long departmentTagId);

    ResponseEntity<?> deleteDepartmentTag(Long departmentTagId);
}
