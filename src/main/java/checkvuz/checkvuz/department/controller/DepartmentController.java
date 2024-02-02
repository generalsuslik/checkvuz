package checkvuz.checkvuz.department.controller;

import checkvuz.checkvuz.department.entity.Department;
import checkvuz.checkvuz.department.entity.DepartmentTag;
import checkvuz.checkvuz.department.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping("")
    public CollectionModel<EntityModel<Department>> getDepartments() {
        return departmentService.getDepartments();
    }

    @GetMapping("/{departmentId}")
    public EntityModel<Department> getDepartment(@PathVariable Long departmentId) {
        return departmentService.getDepartment(departmentId);
    }

    @PutMapping("/department/{departmentId}")
    public ResponseEntity<EntityModel<Department>> updateDepartment(@RequestBody Department departmentToUpdate,
                                                                    @PathVariable Long departmentId) {
        return departmentService.updateDepartment(departmentToUpdate, departmentId);
    }

    @DeleteMapping("/department/{departmentId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long departmentId) {
        return departmentService.deleteDepartment(departmentId);
    }

    @GetMapping("/department/{departmentId}/tags")
    public CollectionModel<EntityModel<DepartmentTag>> getAssignedTags(@PathVariable Long departmentId) {
        return departmentService.getAssignedTags(departmentId);
    }

    @PutMapping("/department/{departmentId}/tags/{departmentTagId}")
    public ResponseEntity<EntityModel<Department>> assignTag(@PathVariable Long departmentId,
                                                             @PathVariable Long departmentTagId) {
        return departmentService.assignTag(departmentId, departmentTagId);
    }

    @DeleteMapping("/department/{departmentId}/tags/{departmentTagId}")
    public ResponseEntity<EntityModel<Department>> deleteTag(@PathVariable Long departmentId,
                                                             @PathVariable Long departmentTagId) {
        return departmentService.removeTag(departmentId, departmentTagId);
    }
}
