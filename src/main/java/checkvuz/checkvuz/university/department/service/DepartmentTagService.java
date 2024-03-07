package checkvuz.checkvuz.university.department.service;

import checkvuz.checkvuz.university.department.assembler.DepartmentTagModelAssembler;
import checkvuz.checkvuz.university.department.controller.DepartmentTagController;
import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import checkvuz.checkvuz.university.department.exception.DepartmentTagNotFoundException;
import checkvuz.checkvuz.university.department.repository.DepartmentTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class DepartmentTagService implements DepartmentTagServiceInterface {

    private final DepartmentTagModelAssembler departmentTagModelAssembler;
    private final DepartmentTagRepository departmentTagRepository;

    @Override
    public CollectionModel<EntityModel<DepartmentTag>> getDepartmentTags() {

        List<EntityModel<DepartmentTag>> departmentTags = departmentTagRepository.findAll().stream()
                .map(departmentTagModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(departmentTags,
                linkTo(methodOn(DepartmentTagController.class).getDepartmentTags()).withSelfRel());
    }

    @Override
    public ResponseEntity<EntityModel<DepartmentTag>> createDepartmentTag(DepartmentTag tagToCreate) {

        EntityModel<DepartmentTag> entityModel = departmentTagModelAssembler
                .toModel(departmentTagRepository.save(tagToCreate));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public EntityModel<DepartmentTag> getDepartmentTag(Long departmentTagId) {

        DepartmentTag departmentTag = departmentTagRepository
                .findById(departmentTagId).orElseThrow(() -> new DepartmentTagNotFoundException(departmentTagId));

        return departmentTagModelAssembler.toModel(departmentTag);
    }

    @Override
    public ResponseEntity<EntityModel<DepartmentTag>> updateDepartmentTag(DepartmentTag departmentTagToUpdate,
                                                                          Long departmentTagId) {

        DepartmentTag updatedDepartmentTag = departmentTagRepository.findById(departmentTagId)
                .map(departmentTag -> {
                    departmentTag.setId(departmentTagToUpdate.getId());
                    departmentTag.setTitle(departmentTagToUpdate.getTitle());
                    return departmentTagRepository.save(departmentTag);
                })
                .orElseGet(() -> {
                    departmentTagToUpdate.setId(departmentTagId);
                    return departmentTagRepository.save(departmentTagToUpdate);
                });

        EntityModel<DepartmentTag> entityModel = departmentTagModelAssembler.toModel(updatedDepartmentTag);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public ResponseEntity<?> deleteDepartmentTag(Long departmentTagId) {

        departmentTagRepository.deleteById(departmentTagId);

        return ResponseEntity.noContent().build();
    }
}
