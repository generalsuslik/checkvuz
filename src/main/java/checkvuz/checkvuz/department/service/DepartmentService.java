package checkvuz.checkvuz.department.service;

import checkvuz.checkvuz.department.assembler.DepartmentModelAssembler;
import checkvuz.checkvuz.department.assembler.DepartmentTagModelAssembler;
import checkvuz.checkvuz.department.controller.DepartmentController;
import checkvuz.checkvuz.department.controller.DepartmentTagController;
import checkvuz.checkvuz.department.entity.Department;
import checkvuz.checkvuz.department.entity.DepartmentTag;
import checkvuz.checkvuz.department.exception.DepartmentNotFoundException;
import checkvuz.checkvuz.department.exception.DepartmentTagNotFoundException;
import checkvuz.checkvuz.department.repository.DepartmentRepository;
import checkvuz.checkvuz.department.repository.DepartmentTagRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class DepartmentService implements DepartmentServiceInterface {

    private final DepartmentModelAssembler departmentModelAssembler;
    private final DepartmentRepository departmentRepository;

    private final DepartmentTagModelAssembler departmentTagModelAssembler;
    private final DepartmentTagRepository departmentTagRepository;


    // MAIN DEPARTMENT SECTION
    @Override
    public CollectionModel<EntityModel<Department>> getDepartments() {

        List<EntityModel<Department>> departments = departmentRepository.findAll().stream()
                .map(departmentModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(departments,
                linkTo(methodOn(DepartmentController.class).getDepartments()).withSelfRel());
    }

    @Override
    public EntityModel<Department> getDepartment(Long departmentId) {

        Department department = departmentRepository
                .findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));

        return departmentModelAssembler.toModel(department);
    }

    @Override
    @Transactional
    public ResponseEntity<EntityModel<Department>> updateDepartment(Department departmentToUpdate, Long departmentId) {

        Department updatedDepartment = departmentRepository.findById(departmentId)
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

        EntityModel<Department> entityModel = departmentModelAssembler.toModel(updatedDepartment);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteDepartment(Long departmentId) {

        departmentRepository.deleteById(departmentId);

        return ResponseEntity.noContent().build();
    }

    // DEPARTMENT TAG SECTION
    @Override
    public CollectionModel<EntityModel<DepartmentTag>> getAssignedTags(Long departmentId) {

        Department department = departmentRepository
                .findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        List<DepartmentTag> departmentTags = departmentTagRepository.findAll().stream().toList();

        List<EntityModel<DepartmentTag>> assignedTags = new ArrayList<>();
        for (DepartmentTag tag : departmentTags) {
            if (department.getDepartmentTags().contains(tag)) {
                assignedTags.add(departmentTagModelAssembler.toModel(tag));
            }
        }

        return CollectionModel.of(assignedTags,
                linkTo(methodOn(DepartmentTagController.class).getDepartmentTags()).withSelfRel());
    }

    @Override
    @Transactional
    public ResponseEntity<EntityModel<Department>> assignTag(Long departmentId, Long departmentTagId) {

        Department department = departmentRepository
                .findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        DepartmentTag departmentTag = departmentTagRepository
                .findById(departmentTagId).orElseThrow(() -> new DepartmentTagNotFoundException(departmentTagId));

        department.getDepartmentTags().add(departmentTag);
        departmentRepository.save(department);

        EntityModel<Department> entityModel = departmentModelAssembler.toModel(department);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    @Transactional
    public ResponseEntity<EntityModel<Department>> removeTag(Long departmentId, Long departmentTagId) {

        Department department = departmentRepository
                .findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        DepartmentTag departmentTag = departmentTagRepository
                .findById(departmentTagId).orElseThrow(() -> new DepartmentTagNotFoundException(departmentTagId));

        department.getDepartmentTags().remove(departmentTag);
        departmentRepository.save(department);

        EntityModel<Department> entityModel = departmentModelAssembler.toModel(department);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
