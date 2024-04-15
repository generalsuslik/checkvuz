package checkvuz.checkvuz.university.faculty.service;

import checkvuz.checkvuz.university.department.entity.Department;
import checkvuz.checkvuz.university.department.service.DepartmentService;
import checkvuz.checkvuz.university.faculty.assembler.FacultyModelAssembler;
import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import checkvuz.checkvuz.university.faculty.exception.FacultyNotFoundException;
import checkvuz.checkvuz.university.faculty.repository.FacultyRepository;
import checkvuz.checkvuz.university.program.entity.Program;
import checkvuz.checkvuz.university.program.service.ProgramService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyService implements FacultyServiceInterface {

    private final FacultyModelAssembler facultyModelAssembler;
    private final FacultyRepository facultyRepository;

    private final DepartmentService departmentService;
    private final ProgramService programService;
    private final FacultyTagService facultyTagService;


    @Override
    public List<Faculty> getAllFaculties() {

        return new ArrayList<>(facultyRepository.findAll());
    }

    @Override
    public Faculty saveFaculty(Faculty faculty) {

        return facultyRepository.save(faculty);
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
                    faculty.setTitle(facultyToUpdate.getTitle());
                    faculty.setExpandedTitle(facultyToUpdate.getExpandedTitle());
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

    // FACULTY DEPARTMENT SECTION
    @Override
    public List<Department> getAssignedDepartments(Long facultyId) {

        Faculty faculty = getFaculty(facultyId);
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

        return getAssignedDepartments(facultyId).stream()
                .map(departmentService::convertDepartmentToModel)
                .collect(Collectors.toList());
    }

    @Override
    public EntityModel<Department> createAndAssignDepartment(Department departmentToCreate, Long facultyId) {

        Faculty faculty = getFaculty(facultyId);

        departmentToCreate.setFaculty(faculty);
        return departmentService.convertDepartmentToModel(
                departmentService.createDepartment(departmentToCreate)
        );
    }

    // FACULTY STUDY PROGRAMS SECTION
    @Override
    public List<Program> getPrograms(Long facultyId) {

        Faculty faculty = getFaculty(facultyId);
        return programService.getPrograms().stream()
                .filter(program -> faculty.getPrograms().contains(program))
                .collect(Collectors.toList());
    }

    @Override
    public List<EntityModel<Program>> getProgramModels(Long facultyId) {

        return getPrograms(facultyId).stream()
                .map(programService::convertProgramToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Faculty addProgram(Long facultyId, Long programId) {

        Faculty faculty = getFaculty(facultyId);
        Program program = programService.getProgramById(programId);

        faculty.getPrograms().add(program);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty removeProgram(Long facultyId, Long programId) {

        Faculty faculty = getFaculty(facultyId);
        Program program = programService.getProgramById(programId);

        faculty.getPrograms().remove(program);
        return facultyRepository.save(faculty);
    }

    // FACULTY TAGS SECTION
    @Override
    public List<FacultyTag> getAssignedTags(Long facultyId) {
        Faculty faculty = getFaculty(facultyId);

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

        return getAssignedTags(facultyId).stream()
                .map(facultyTagService::convertFacultyTagToModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Faculty assignTag(Long facultyId, Long facultyTagId) {

        Faculty faculty = getFaculty(facultyId);
        FacultyTag facultyTag = facultyTagService.getFacultyTag(facultyTagId);

        faculty.getFacultyTags().add(facultyTag);
        return facultyRepository.save(faculty);
    }

    @Override
    @Transactional
    public Faculty removeTag(Long facultyId, Long facultyTagId) {

        Faculty faculty = getFaculty(facultyId);
        FacultyTag facultyTag = facultyTagService.getFacultyTag(facultyTagId);

        faculty.getFacultyTags().remove(facultyTag);
        return facultyRepository.save(faculty);
    }
}
