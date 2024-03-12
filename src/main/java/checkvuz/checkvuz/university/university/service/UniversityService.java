package checkvuz.checkvuz.university.university.service;

import checkvuz.checkvuz.university.faculty.assembler.FacultyModelAssembler;
import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.faculty.repository.FacultyRepository;
import checkvuz.checkvuz.university.university.assembler.UniversityModelAssembler;
import checkvuz.checkvuz.university.university.controller.UniversityController;
import checkvuz.checkvuz.university.university.entity.University;
import checkvuz.checkvuz.university.university.entity.UniversityTag;
import checkvuz.checkvuz.university.university.exception.UniversityNotFoundException;
import checkvuz.checkvuz.university.university.repository.UniversityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class UniversityService implements UniversityServiceInterface {

    private final UniversityModelAssembler universityModelAssembler;
    private final UniversityRepository universityRepository;

    private final UniversityTagService universityTagService;

    private final FacultyModelAssembler facultyModelAssembler;
    private final FacultyRepository facultyRepository;

    // get all universities
    @Override
    public List<University> getUniversities() {

        return new ArrayList<>(universityRepository.findAll());
    }

    // create new university
    @Override
    @Transactional
    public University createUniversity(University universityToCreate) {
        return universityRepository.save(universityToCreate);
    }

    // get one university by its id
    @Override
    public University getUniversity(Long universityId) {
        return universityRepository.findById(universityId)
                .orElseThrow(() -> new UniversityNotFoundException(universityId));
    }

    // update university info
    @Override
    @Transactional
    public University updateUniversity(University universityToUpdate, Long universityId) {

        return universityRepository.findById(universityId)
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
    }

    // delete university
    @Override
    @Transactional
    public void deleteUniversity(Long universityId) {

        universityRepository.deleteById(universityId);
    }

    // return university model
    @Override
    public EntityModel<University> convertUniversityToModel(University university) {
        return universityModelAssembler.toModel(university);
    }


    // UNIVERSITY TAGS SECTION
    // get tags that are assigned to one specific university
    @Override
    public List<UniversityTag> getAssignedTags(Long universityId) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));

        List<UniversityTag> tags = universityTagService.getUniversityTags();

        List<UniversityTag> universityTags = new ArrayList<>();
        for (UniversityTag tag : tags) {
            if (university.getUniversityTags().contains(tag)) {
                universityTags.add(tag);
            }
        }

        return universityTags;
    }

    @Override
    public List<EntityModel<UniversityTag>> getAssignedEntityTags(Long universityId) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));

        List<UniversityTag> tags = universityTagService.getUniversityTags();

        List<EntityModel<UniversityTag>> universityTags = new ArrayList<>();
        for (UniversityTag tag : tags) {
            if (university.getUniversityTags().contains(tag)) {
                universityTags.add(universityTagService.convertUniversityTagToModel(tag));
            }
        }

        return universityTags;
    }

    // assign tag to one specific university
    @Override
    @Transactional
    public University assignTag(Long universityId, Long tagId) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));
        UniversityTag universityTag = universityTagService.getUniversityTag(tagId);

        university.getUniversityTags().add(universityTag);
        return universityRepository.save(university);
    }

    // remove tag from university
    @Override
    @Transactional
    public University removeTag(Long universityId, Long tagId) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));
        UniversityTag universityTag = universityTagService.getUniversityTag(tagId);

        university.getUniversityTags().remove(universityTag);
        return universityRepository.save(university);
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

    @Override
    @Transactional
    public ResponseEntity<EntityModel<Faculty>> createAndAssignFaculty(Long universityId, Faculty facultyToCreate) {

        University university = universityRepository
                .findById(universityId).orElseThrow(() -> new UniversityNotFoundException(universityId));

        facultyToCreate.setUniversity(university);
        EntityModel<Faculty> entityModel = facultyModelAssembler.toModel(facultyRepository.save(facultyToCreate));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
