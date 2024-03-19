package checkvuz.checkvuz.university.department.controller;

import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import checkvuz.checkvuz.university.department.service.DepartmentTagService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/department-tags")
@AllArgsConstructor
public class DepartmentTagController {

    private DepartmentTagService departmentTagService;

    @GetMapping("")
    public CollectionModel<EntityModel<DepartmentTag>> getDepartmentTags() {

        List<DepartmentTag> departmentTags = departmentTagService.getDepartmentTags();
        List<EntityModel<DepartmentTag>> departmentTagModels = departmentTags.stream()
                .map(departmentTagService::convertDepartmentTagToModel)
                .collect(Collectors.toList());

        return CollectionModel.of(departmentTagModels,
                linkTo(methodOn(DepartmentTagController.class).getDepartmentTags()).withSelfRel());
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<DepartmentTag>> createDepartmentTag(@RequestBody DepartmentTag tagToCreate) {

        DepartmentTag departmentTag = departmentTagService.createDepartmentTag(tagToCreate);
        EntityModel<DepartmentTag> entityModel = departmentTagService.convertDepartmentTagToModel(departmentTag);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/{departmentTagId}")
    public EntityModel<DepartmentTag> getDepartmentTag(@PathVariable Long departmentTagId) {
        return departmentTagService.convertDepartmentTagToModel(
                departmentTagService.getDepartmentTag(departmentTagId)
        );
    }

    @PutMapping("/{departmentTagId}")
    public ResponseEntity<EntityModel<DepartmentTag>> updateDepartmentTag(@RequestBody DepartmentTag tagToUpdate,
                                                                          @PathVariable Long departmentTagId) {

        EntityModel<DepartmentTag> entityModel = departmentTagService.convertDepartmentTagToModel(
                departmentTagService.updateDepartmentTag(tagToUpdate, departmentTagId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{departmentTagId}")
    public ResponseEntity<?> deleteDepartmentTag(@PathVariable Long departmentTagId) {

        departmentTagService.deleteDepartmentTag(departmentTagId);
        return ResponseEntity.noContent().build();
    }
}
