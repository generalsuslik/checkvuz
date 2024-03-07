package checkvuz.checkvuz.university.department.controller;

import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import checkvuz.checkvuz.university.department.service.DepartmentTagService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department-tags")
@AllArgsConstructor
public class DepartmentTagController {

    private DepartmentTagService departmentTagService;

    @GetMapping("")
    public CollectionModel<EntityModel<DepartmentTag>> getDepartmentTags() {
        return departmentTagService.getDepartmentTags();
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<DepartmentTag>> createDepartmentTag(@RequestBody DepartmentTag tagToCreate) {
        return departmentTagService.createDepartmentTag(tagToCreate);
    }

    @GetMapping("/{departmentTagId}")
    public EntityModel<DepartmentTag> getDepartmentTag(@PathVariable Long departmentTagId) {
        return departmentTagService.getDepartmentTag(departmentTagId);
    }

    @PutMapping("/{departmentTagId}")
    public ResponseEntity<EntityModel<DepartmentTag>> updateDepartmentTag(@RequestBody DepartmentTag tagToUpdate,
                                                                          @PathVariable Long departmentTagId) {
        return departmentTagService.updateDepartmentTag(tagToUpdate, departmentTagId);
    }

    @DeleteMapping("/{departmentTagId}")
    public ResponseEntity<?> deleteDepartmentTag(@PathVariable Long departmentTagId) {
        return departmentTagService.deleteDepartmentTag(departmentTagId);
    }
}
