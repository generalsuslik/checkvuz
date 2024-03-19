package checkvuz.checkvuz.university.department.service;

import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface DepartmentTagServiceInterface {

   List<DepartmentTag> getDepartmentTags();

    DepartmentTag createDepartmentTag(DepartmentTag tagToCreate);

    DepartmentTag getDepartmentTag(Long departmentTagId);

    DepartmentTag updateDepartmentTag(DepartmentTag departmentTagToUpdate, Long departmentTagId);

    void deleteDepartmentTag(Long departmentTagId);

    EntityModel<DepartmentTag> convertDepartmentTagToModel(DepartmentTag departmentTag);
}
