package checkvuz.checkvuz.university.faculty.service;

import checkvuz.checkvuz.university.department.entity.Department;
import checkvuz.checkvuz.university.department.service.DepartmentService;
import checkvuz.checkvuz.university.faculty.assembler.FacultyModelAssembler;
import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import checkvuz.checkvuz.university.faculty.exception.FacultyNotFoundException;
import checkvuz.checkvuz.university.faculty.repository.FacultyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class FacultyService implements FacultyServiceInterface {

    private final FacultyModelAssembler facultyModelAssembler;
    private final FacultyRepository facultyRepository;

    private final FacultyTagService facultyTagService;

    private final DepartmentService departmentService;


    @Override
    public List<Faculty> getAllFaculties() {

        return new ArrayList<>(facultyRepository.findAll());
    }

    @Override
    public Faculty getFaculty(Long facultyId) {

        return facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));
    }

    @Override
    @Transactional
    public Faculty updateFaculty(Faculty facultyToUpdate, Long facultyId) {

        return facultyRepository.findById(facultyId)
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
    }

    @Override
    @Transactional
    public void deleteFaculty(Long facultyId) {

        facultyRepository.deleteById(facultyId);
    }

    @Override
    public EntityModel<Faculty> convertFacultyToModel(Faculty faculty) {
        return facultyModelAssembler.toModel(faculty);
    }

    // FACULTY TAGS SECTION
    @Override
    public List<FacultyTag> getAssignedTags(Long facultyId) {
        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));

        List<FacultyTag> tags = facultyTagService.getFacultyTags();

        List<FacultyTag> facultyTags = new ArrayList<>();
        for (FacultyTag tag : tags) {
            if (faculty.getFacultyTags().contains(tag)) {
                facultyTags.add(tag);
            }
        }

        return facultyTags;
    }

    @Override
    public List<EntityModel<FacultyTag>> getAssignedTagsModels(Long facultyId) {
        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));

        List<FacultyTag> tags = facultyTagService.getFacultyTags();

        List<EntityModel<FacultyTag>> facultyTags = new ArrayList<>();
        for (FacultyTag tag : tags) {
            if (faculty.getFacultyTags().contains(tag)) {
                facultyTags.add(facultyTagService.convertFacultyTagToModel(tag));
            }
        }

        return facultyTags;
    }

    @Override
    @Transactional
    public Faculty assignTag(Long facultyId, Long facultyTagId) {
        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));
        FacultyTag facultyTag = facultyTagService.getFacultyTag(facultyTagId);

        faculty.getFacultyTags().add(facultyTag);
        return facultyRepository.save(faculty);
    }

    @Override
    @Transactional
    public Faculty removeTag(Long facultyId, Long facultyTagId) {
        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));
        FacultyTag facultyTag = facultyTagService.getFacultyTag(facultyTagId);

        faculty.getFacultyTags().remove(facultyTag);
        return facultyRepository.save(faculty);
    }


    // FACULTY DEPARTMENT SECTION
    @Override
    public List<Department> getAssignedDepartments(Long facultyId) {

        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));
        List<Department> departments = departmentService.getDepartments();

        List<Department> assignedDepartments = new ArrayList<>();
        for (Department department : departments) {
            if (department.getFaculty() == faculty) {
                assignedDepartments.add(department);
            }
        }

        return assignedDepartments;
    }

    @Override
    public List<EntityModel<Department>> getAssignedDepartmentsModels(Long facultyId) {
        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));
        List<Department> departments = departmentService.getDepartments();

        List<EntityModel<Department>> assignedDepartments = new ArrayList<>();
        for (Department department : departments) {
            if (department.getFaculty() == faculty) {
                assignedDepartments.add(departmentService.convertDepartmentToModel(department));
            }
        }

        return assignedDepartments;
    }

    @Override
    public EntityModel<Department> createAndAssignDepartment(Department departmentToCreate, Long facultyId) {

        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));

        departmentToCreate.setFaculty(faculty);
        return departmentService.convertDepartmentToModel(
                departmentService.createDepartment(departmentToCreate)
        );
    }
}
