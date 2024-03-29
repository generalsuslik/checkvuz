package checkvuz.checkvuz.university.department.controller;

import checkvuz.checkvuz.university.department.entity.Department;
import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import checkvuz.checkvuz.university.department.service.DepartmentService;
import checkvuz.checkvuz.university.program.controller.ProgramController;
import checkvuz.checkvuz.university.program.entity.Program;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping("")
    public CollectionModel<EntityModel<Department>> getDepartments() {

        List<Department> departments = departmentService.getDepartments();

        List<EntityModel<Department>> entityDepartments = departments.stream()
                .map(departmentService::convertDepartmentToModel)
                .toList();

        return CollectionModel.of(entityDepartments,
                linkTo(methodOn(DepartmentController.class).getDepartments()).withSelfRel());
    }

    @GetMapping("/{departmentId}")
    public EntityModel<Department> getDepartment(@PathVariable Long departmentId) {
        return departmentService.convertDepartmentToModel(
                departmentService.getDepartment(departmentId)
        );
    }

    @PutMapping("/department/{departmentId}")
    public ResponseEntity<EntityModel<Department>> updateDepartment(@RequestBody Department departmentToUpdate,
                                                                    @PathVariable Long departmentId) {

        EntityModel<Department> entityModel = departmentService.convertDepartmentToModel(
                departmentService.updateDepartment(departmentToUpdate, departmentId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/department/{departmentId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long departmentId) {

        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.noContent().build();
    }

    // DEPARTMENT PROGRAMS SECTION
    @GetMapping("/{departmentId}/programs")
    public CollectionModel<EntityModel<Program>> getDepartmentPrograms(@PathVariable Long departmentId) {

        List<EntityModel<Program>> programs = departmentService.getProgramModels(departmentId);

        return CollectionModel.of(programs,
                linkTo(methodOn(ProgramController.class).getPrograms()).withRel("programs")
        );
    }

    @PutMapping("/{departmentId}/programs/{programId}")
    public ResponseEntity<EntityModel<Department>> addProgram(@PathVariable Long departmentId,
                                                           @PathVariable Long programId) {

       EntityModel<Department> department = departmentService.convertDepartmentToModel(
                departmentService.addProgram(departmentId, programId)
        );
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{departmentId}/programs/{programId}")
    public ResponseEntity<EntityModel<Department>> removeProgram(@PathVariable Long departmentId,
                                                              @PathVariable Long programId) {

        EntityModel<Department> department = departmentService.convertDepartmentToModel(
                departmentService.removeProgram(departmentId, programId)
        );
        return ResponseEntity.ok(department);
    }


    // DEPARTMENT TAGS SECTION
    @GetMapping("/{departmentId}/tags")
    public CollectionModel<EntityModel<DepartmentTag>> getAssignedTags(@PathVariable Long departmentId) {

        List<EntityModel<DepartmentTag>> assignedTags = departmentService.getAssignedTagsModels(departmentId);
        return CollectionModel.of(assignedTags,
                linkTo(methodOn(DepartmentController.class).getAssignedTags(departmentId)).withSelfRel());
    }

    @PutMapping("/{departmentId}/tags/{departmentTagId}")
    public ResponseEntity<EntityModel<Department>> assignTag(@PathVariable Long departmentId,
                                                             @PathVariable Long departmentTagId) {

        Department department = departmentService.assignTag(departmentId, departmentTagId);
        EntityModel<Department> entityModel = departmentService.convertDepartmentToModel(department);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{departmentId}/tags/{departmentTagId}")
    public ResponseEntity<EntityModel<Department>> deleteTag(@PathVariable Long departmentId,
                                                             @PathVariable Long departmentTagId) {

        Department department = departmentService.removeTag(departmentId, departmentTagId);
        EntityModel<Department> entityModel = departmentService.convertDepartmentToModel(department);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
