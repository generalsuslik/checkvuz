package checkvuz.checkvuz.faculty.service;

import checkvuz.checkvuz.department.assembler.DepartmentModelAssembler;
import checkvuz.checkvuz.department.controller.DepartmentController;
import checkvuz.checkvuz.department.entity.Department;
import checkvuz.checkvuz.department.repository.DepartmentRepository;
import checkvuz.checkvuz.faculty.assembler.FacultyModelAssembler;
import checkvuz.checkvuz.faculty.assembler.FacultyTagModelAssembler;
import checkvuz.checkvuz.faculty.controller.FacultyController;
import checkvuz.checkvuz.faculty.controller.FacultyTagController;
import checkvuz.checkvuz.faculty.entity.Faculty;
import checkvuz.checkvuz.faculty.entity.FacultyTag;
import checkvuz.checkvuz.faculty.exception.FacultyNotFoundException;
import checkvuz.checkvuz.faculty.exception.FacultyTagNotFoundException;
import checkvuz.checkvuz.faculty.repository.FacultyRepository;
import checkvuz.checkvuz.faculty.repository.FacultyTagRepository;
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
public class FacultyService implements FacultyServiceInterface {

    private final FacultyModelAssembler facultyModelAssembler;
    private final FacultyRepository facultyRepository;

    private final FacultyTagModelAssembler facultyTagModelAssembler;
    private final FacultyTagRepository facultyTagRepository;

    private final DepartmentModelAssembler departmentModelAssembler;
    private final DepartmentRepository departmentRepository;

    @Override
    public CollectionModel<EntityModel<Faculty>> getAllFaculties() {

        List<EntityModel<Faculty>> faculties = facultyRepository.findAll().stream()
                .map(facultyModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(faculties,
                linkTo(methodOn(FacultyController.class).getAllFaculties()).withSelfRel());
    }

    @Override
    public EntityModel<Faculty> getFaculty(Long facultyId) {

        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));

        return facultyModelAssembler.toModel(faculty);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateFaculty(Faculty facultyToUpdate, Long facultyId) {

        Faculty updatedFaculty = facultyRepository.findById(facultyId)
                .map(faculty -> {
                    faculty.setId(facultyToUpdate.getId());
                    faculty.setTitle(facultyToUpdate.getTitle());
                    faculty.setExpanded_title(facultyToUpdate.getExpanded_title());
                    faculty.setDescription(facultyToUpdate.getDescription());
                    faculty.setUniversity(facultyToUpdate.getUniversity());
                    faculty.setFacultyTags(facultyToUpdate.getFacultyTags());
                    return facultyRepository.save(faculty);
                })
                .orElseGet(() -> {
                    facultyToUpdate.setId(facultyId);
                    return facultyRepository.save(facultyToUpdate);
                });

        EntityModel<Faculty> entityModel = facultyModelAssembler.toModel(updatedFaculty);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteFaculty(Long facultyId) {

        facultyRepository.deleteById(facultyId);

        return ResponseEntity.noContent().build();
    }

    // FACULTY TAGS SECTION
    @Override
    public CollectionModel<EntityModel<FacultyTag>> getAssignedTags(Long facultyId) {

        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));

        List<FacultyTag> tags = facultyTagRepository.findAll().stream().toList();

        List<EntityModel<FacultyTag>> facultyTags = new ArrayList<>();
        for (FacultyTag facultyTag : tags) {
            if (faculty.getFacultyTags().contains(facultyTag)) {
                facultyTags.add(facultyTagModelAssembler.toModel(facultyTag));
            }
        }

        return CollectionModel.of(facultyTags,
                linkTo(methodOn(FacultyTagController.class).getFacultyTags()).withSelfRel());
    }

    @Override
    @Transactional
    public ResponseEntity<EntityModel<Faculty>> assignTag(Long facultyId, Long facultyTagId) {
        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));
        FacultyTag facultyTag = facultyTagRepository
                .findById(facultyTagId).orElseThrow(() -> new FacultyTagNotFoundException(facultyTagId));

        faculty.getFacultyTags().add(facultyTag);
        facultyRepository.save(faculty);

        EntityModel<Faculty> entityModel = facultyModelAssembler.toModel(faculty);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    @Transactional
    public ResponseEntity<?> removeTag(Long facultyId, Long facultyTagId) {
        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));
        FacultyTag facultyTag = facultyTagRepository
                .findById(facultyId).orElseThrow(() -> new FacultyTagNotFoundException(facultyTagId));

        faculty.getFacultyTags().remove(facultyTag);
        facultyRepository.save(faculty);

        EntityModel<Faculty> facultyEntityModel = facultyModelAssembler.toModel(faculty);
        return ResponseEntity
                .created(facultyEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(facultyEntityModel);
    }


    // FACULTY DEPARTMENT SECTION
    @Override
    public CollectionModel<EntityModel<Department>> getAssignedDepartments(Long facultyId) {

        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));
        List<Department> departments = departmentRepository.findAll().stream().toList();

        List<EntityModel<Department>> assignedDepartments = new ArrayList<>();
        for (Department department : departments) {
            if (department.getFaculty() == faculty) {
                assignedDepartments.add(departmentModelAssembler.toModel(department));
            }
        }

        return CollectionModel.of(assignedDepartments,
                linkTo(methodOn(DepartmentController.class).getDepartments()).withSelfRel());
    }

    @Override
    public ResponseEntity<EntityModel<Department>> createAndAssignDepartment(Department departmentToCreate,
                                                                             Long facultyId) {

        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));

        departmentToCreate.setFaculty(faculty);
        EntityModel<Department> entityModel = departmentModelAssembler.toModel(
                departmentRepository.save(departmentToCreate)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
