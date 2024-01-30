package checkvuz.checkvuz.university.service;

import checkvuz.checkvuz.faculty.assembler.FacultyModelAssembler;
import checkvuz.checkvuz.faculty.entity.Faculty;
import checkvuz.checkvuz.faculty.repository.FacultyRepository;
import checkvuz.checkvuz.university.assembler.UniversityModelAssembler;
import checkvuz.checkvuz.university.assembler.UniversityTagModelAssembler;
import checkvuz.checkvuz.university.controller.UniversityController;
import checkvuz.checkvuz.university.controller.UniversityTagController;
import checkvuz.checkvuz.university.entity.University;
import checkvuz.checkvuz.university.entity.UniversityTag;
import checkvuz.checkvuz.university.exception.UniversityNotFoundException;
import checkvuz.checkvuz.university.exception.UniversityTagNotFoundException;
import checkvuz.checkvuz.university.repository.UniversityRepository;
import checkvuz.checkvuz.university.repository.UniversityTagRepository;
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
public class UniversityService implements UniversityServiceInterface {

    private final UniversityModelAssembler universityModelAssembler;
    private final UniversityRepository universityRepository;

    private final UniversityTagRepository universityTagRepository;
    private final UniversityTagModelAssembler universityTagModelAssembler;

    private final FacultyModelAssembler facultyModelAssembler;
    private final FacultyRepository facultyRepository;

    // get all universities
    @Override
    public CollectionModel<EntityModel<University>> getUniversities() {

        List<EntityModel<University>> universities = universityRepository.findAll().stream()
                .map(universityModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(universities,
                linkTo(methodOn(UniversityController.class).getUniversities()).withSelfRel());
    }

    // create new university
    @Override
    public ResponseEntity<?> createUniversity(University universityToCreate) {

        EntityModel<University> entityModel =
                universityModelAssembler.toModel(universityRepository.save(universityToCreate));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // get one university by its id
    @Override
    public EntityModel<University> getUniversity(Long universityId) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));

        return universityModelAssembler.toModel(university);
    }

    // update university info
    @Override
    public ResponseEntity<?> updateUniversity(University universityToUpdate, Long universityId) {

        University updatedUniversity = universityRepository.findById(universityId)
                .map(university -> {
                    university.setId(universityToUpdate.getId());
                    university.setTitle(universityToUpdate.getTitle());
                    university.setExpandedTitle(universityToUpdate.getExpandedTitle());
                    university.setDescription(universityToUpdate.getDescription());
                    university.setFoundingYear(universityToUpdate.getFoundingYear());
                    university.setUniversityTags(universityToUpdate.getUniversityTags());
                    return universityRepository.save(university);
                })
                .orElseGet(() -> {
                    universityToUpdate.setId(universityId);
                    return universityRepository.save(universityToUpdate);
                });

        EntityModel<University> entityModel = universityModelAssembler.toModel(updatedUniversity);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // delete university
    @Override
    public ResponseEntity<?> deleteUniversity(Long universityId) {

        universityRepository.deleteById(universityId);

        return ResponseEntity.noContent().build();
    }

    // UNIVERSITY TAGS SECTION
    // get tags that are assigned to one specific university
    @Override
    public CollectionModel<EntityModel<UniversityTag>> getAssignedTags(Long universityId) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));

        List<UniversityTag> tags = universityTagRepository.findAll().stream().toList();

        List<UniversityTag> universityTags = new ArrayList<>();
        for (UniversityTag tag : tags) {
            if (university.getUniversityTags().contains(tag)) {
                universityTags.add(tag);
            }
        }

        List<EntityModel<UniversityTag>> entityUniversityTags = universityTags.stream()
                .map(universityTagModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(entityUniversityTags,
                linkTo(methodOn(UniversityTagController.class).getUniversityTags()).withSelfRel());
    }

    // assign tag to one specific university
    @Override
    @Transactional
    public ResponseEntity<?> assignTag(Long universityId, Long tagId) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));
        UniversityTag universityTag = universityTagRepository
                .findById(tagId).orElseThrow(() -> new UniversityTagNotFoundException(tagId));

        university.getUniversityTags().add(universityTag);
        universityRepository.save(university);

        EntityModel<University> entityModel = universityModelAssembler.toModel(university);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // remove tag from university
    @Override
    @Transactional
    public ResponseEntity<?> removeTag(Long universityId, Long tagId) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));
        UniversityTag universityTag = universityTagRepository
                .findById(tagId).orElseThrow(() -> new UniversityTagNotFoundException(tagId));

        university.getUniversityTags().remove(universityTag);
        universityRepository.save(university);

        EntityModel<University> entityModel = universityModelAssembler.toModel(university);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // UNIVERSITY FACULTIES SECTION
    @Override
    public CollectionModel<EntityModel<Faculty>> getUniversityFaculties(Long universityId) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));

        List<EntityModel<Faculty>> universityFaculties = new ArrayList<>();

        for (Faculty faculty : facultyRepository.findAll()) {
            if (faculty.getUniversity() == university) {
                universityFaculties.add(facultyModelAssembler.toModel(faculty));
            }
        }

        return CollectionModel.of(universityFaculties,
                linkTo(methodOn(UniversityController.class).getUniversityFaculties(universityId)).withSelfRel());
    }
}
