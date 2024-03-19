package checkvuz.checkvuz.university.department.service;

import checkvuz.checkvuz.university.department.assembler.DepartmentTagModelAssembler;
import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import checkvuz.checkvuz.university.department.exception.DepartmentTagNotFoundException;
import checkvuz.checkvuz.university.department.repository.DepartmentTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentTagService implements DepartmentTagServiceInterface {

    private final DepartmentTagModelAssembler departmentTagModelAssembler;
    private final DepartmentTagRepository departmentTagRepository;

    @Override
    public List<DepartmentTag> getDepartmentTags() {

        return new ArrayList<>(departmentTagRepository.findAll());
    }

    @Override
    public DepartmentTag createDepartmentTag(DepartmentTag tagToCreate) {

        return departmentTagRepository.save(tagToCreate);
    }

    @Override
    public DepartmentTag getDepartmentTag(Long departmentTagId) {

        return departmentTagRepository
                .findById(departmentTagId).orElseThrow(() -> new DepartmentTagNotFoundException(departmentTagId));
    }

    @Override
    public DepartmentTag updateDepartmentTag(DepartmentTag departmentTagToUpdate,
                                                                          Long departmentTagId) {

        return departmentTagRepository.findById(departmentTagId)
                .map(departmentTag -> {
                    departmentTag.setId(departmentTagToUpdate.getId());
                    departmentTag.setTitle(departmentTagToUpdate.getTitle());
                    return departmentTagRepository.save(departmentTag);
                })
                .orElseGet(() -> {
                    departmentTagToUpdate.setId(departmentTagId);
                    return departmentTagRepository.save(departmentTagToUpdate);
                });
    }

    @Override
    public void deleteDepartmentTag(Long departmentTagId) {

        departmentTagRepository.deleteById(departmentTagId);

    }

    @Override
    public EntityModel<DepartmentTag> convertDepartmentTagToModel(DepartmentTag departmentTag) {
        return null;
    }
}
