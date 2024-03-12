package checkvuz.checkvuz.university.department.service;

import checkvuz.checkvuz.university.department.assembler.DepartmentModelAssembler;
import checkvuz.checkvuz.university.department.entity.Department;
import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import checkvuz.checkvuz.university.department.exception.DepartmentNotFoundException;
import checkvuz.checkvuz.university.department.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService implements DepartmentServiceInterface {

    private final DepartmentModelAssembler departmentModelAssembler;
    private final DepartmentRepository departmentRepository;

    private final DepartmentTagService departmentTagService;

    // MAIN DEPARTMENT SECTION
    @Override
    public List<Department> getDepartments() {

        return new ArrayList<>(departmentRepository.findAll());
    }

    @Override
    public Department createDepartment(Department departmentToCreate) {

        return departmentRepository.save(departmentToCreate);
    }

    @Override
    public Department getDepartment(Long departmentId) {

        return departmentRepository
                .findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
    }

    @Override
    @Transactional
    public Department updateDepartment(Department departmentToUpdate, Long departmentId) {

        return departmentRepository.findById(departmentId)
                .map(department -> {
                    department.setId(departmentToUpdate.getId());
                    department.setTitle(departmentToUpdate.getTitle());
                    department.setExpanded_title(departmentToUpdate.getExpanded_title());
                    department.setDescription(departmentToUpdate.getDescription());
                    department.setFaculty(departmentToUpdate.getFaculty());
                    department.setDepartmentTags(departmentToUpdate.getDepartmentTags());
                    return departmentRepository.save(department);
                })
                .orElseGet(() -> {
                    departmentToUpdate.setId(departmentId);
                    return departmentRepository.save(departmentToUpdate);
                });
    }

    @Override
    @Transactional
    public void deleteDepartment(Long departmentId) {

        departmentRepository.deleteById(departmentId);
    }

    @Override
    public EntityModel<Department> convertDepartmentToModel(Department department) {
        return departmentModelAssembler.toModel(department);
    }

    // DEPARTMENT TAG SECTION
    @Override
    public List<DepartmentTag> getAssignedTags(Long departmentId) {
        Department department = getDepartment(departmentId);
        List<DepartmentTag> departmentTags = departmentTagService.getDepartmentTags();

        List<DepartmentTag> assignedTags = new ArrayList<>();
        for (DepartmentTag tag : departmentTags) {
            if (department.getDepartmentTags().contains(tag)) {
                assignedTags.add(tag);
            }
        }

        return assignedTags;
    }

    @Override
    public List<EntityModel<DepartmentTag>> getAssignedTagsModels(Long departmentId) {

        Department department = getDepartment(departmentId);
        List<DepartmentTag> departmentTags = departmentTagService.getDepartmentTags();

        List<EntityModel<DepartmentTag>> assignedTags = new ArrayList<>();
        for (DepartmentTag tag : departmentTags) {
            if (department.getDepartmentTags().contains(tag)) {
                assignedTags.add(departmentTagService.convertDepartmentTagToModel(tag));
            }
        }

        return assignedTags;
    }

    @Override
    @Transactional
    public Department assignTag(Long departmentId, Long departmentTagId) {

        Department department = departmentRepository
                .findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        DepartmentTag departmentTag = departmentTagService.getDepartmentTag(departmentTagId);

        department.getDepartmentTags().add(departmentTag);
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public Department removeTag(Long departmentId, Long departmentTagId) {

        Department department = departmentRepository
                .findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        DepartmentTag departmentTag = departmentTagService.getDepartmentTag(departmentTagId);

        department.getDepartmentTags().remove(departmentTag);
        return departmentRepository.save(department);
    }
}
