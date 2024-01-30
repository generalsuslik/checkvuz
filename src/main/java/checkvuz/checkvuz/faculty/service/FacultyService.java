package checkvuz.checkvuz.faculty.service;

import checkvuz.checkvuz.faculty.assembler.FacultyModelAssembler;
import checkvuz.checkvuz.faculty.controller.FacultyController;
import checkvuz.checkvuz.faculty.entity.Faculty;
import checkvuz.checkvuz.faculty.exception.FacultyNotFoundException;
import checkvuz.checkvuz.faculty.repository.FacultyRepository;
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
public class FacultyService implements FacultyServiceInterface {

    private final FacultyModelAssembler facultyModelAssembler;
    private final FacultyRepository facultyRepository;

    @Override
    public CollectionModel<EntityModel<Faculty>> getAllFaculties() {

        List<EntityModel<Faculty>> faculties = facultyRepository.findAll().stream()
                .map(facultyModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(faculties,
                linkTo(methodOn(FacultyController.class).getAllFaculties()).withSelfRel());
    }

    @Override
    public ResponseEntity<?> createFaculty(Faculty facultyToCreate) {

        EntityModel<Faculty> entityModel = facultyModelAssembler.toModel(facultyRepository.save(facultyToCreate));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public EntityModel<Faculty> getFaculty(Long facultyId) {

        Faculty faculty = facultyRepository
                .findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId));

        return facultyModelAssembler.toModel(faculty);
    }

    @Override
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
    public ResponseEntity<?> deleteFaculty(Long facultyId) {

        facultyRepository.deleteById(facultyId);

        return ResponseEntity.noContent().build();
    }
}
