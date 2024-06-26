package checkvuz.checkvuz.university.university.service;

import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.faculty.service.FacultyService;
import checkvuz.checkvuz.university.program.entity.Program;
import checkvuz.checkvuz.university.program.service.ProgramService;
import checkvuz.checkvuz.university.university.assembler.UniversityModelAssembler;
import checkvuz.checkvuz.university.university.entity.University;
import checkvuz.checkvuz.university.university.entity.UniversityTag;
import checkvuz.checkvuz.university.university.exception.UniversityNotFoundException;
import checkvuz.checkvuz.university.university.repository.UniversityRepository;
import checkvuz.checkvuz.utils.image.entity.Image;
import checkvuz.checkvuz.utils.image.service.ImageService;
import checkvuz.checkvuz.utils.utils.CheckUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UniversityService implements UniversityServiceInterface {

    private final UniversityModelAssembler universityModelAssembler;
    private final UniversityRepository universityRepository;

    private final ImageService imageService;
    private final UniversityTagService universityTagService;
    private final FacultyService facultyService;
    private final ProgramService programService;

    // get all universities
    @Override
    public List<University> getUniversities() {

        return new ArrayList<>(universityRepository.findAll());
    }

    // create new university
    @Override
    @Transactional
    public University createUniversity(University universityToCreate,
                                       @Nullable MultipartFile imageFile) throws IOException {

        if (imageFile != null) {
            Image universityImage = imageService.saveImageToStorage(imageFile, "universities");
            universityToCreate.setUniversityImage(universityImage);
        }

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

    /**
     * ----------------------------------------------------------------------
     * !!! IMPORTANT NOTE !!!:`
     * U CAN'T UPDATE Set<Programs> OR Set<UniversityTags> WITH THIS METHOD
     * ----------------------------------------------------------------------
     * this function updates university in PATCH method
     * params are: {
     *     "id": [Long] universityId,
     *     "title": [String] title,
     *     "slug": [String] slug,
     *     "expandedTitle": [String] expandedTitle,
     *     "description": [String] description,
     *     "foundingYear": [Integer] foundingYear,
     *     "universityImage": [Image] universityImage
     * }
     * ----------------------------------------------------------------------
     * !!! IMPORTANT NOTE !!!:
     * U CAN'T UPDATE Set<Programs> OR Set<UniversityTags> WITH THIS METHOD
     * ----------------------------------------------------------------------
     */
    @Override
    @Transactional
    public University updateUniversity(Map<String, Object> params, Long universityId) {

        // Here we check all pairs of params to have valid key name and value type
        CheckUtils.checkParams(params, University.class);

        // Then we get a university to be updated
        University university = getUniversity(universityId);

        // And now when we iterate over a params hashmap, we set parameter's value to the
        // updating university fields
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String fieldName = entry.getKey();
            var value = entry.getValue();

            switch (fieldName) {
                case "id":
                    university.setId((Long) value);
                    break;

                case "title":
                    university.setTitle((String) value);
                    break;

                case "slug":
                    university.setSlug((String) value);
                    break;

                case "expandedTitle":
                    university.setExpandedTitle((String) value);
                    break;

                case "description":
                    university.setDescription((String) value);
                    break;

                case "foundingYear":
                    university.setFoundingYear((Integer) value);
                    break;

                case "universityImage":
                    university.setUniversityImage((Image) value);
                    break;
            }
        }

        return universityRepository.save(university);
    }

    @Override
    @Transactional
    public University addUniversityImage(Long universityId, MultipartFile imageToAdd) throws IOException {

        Image image = imageService.saveImageToStorage(imageToAdd, "universities");
        University university = getUniversity(universityId);

        university.setUniversityImage(image);
        return universityRepository.save(university);
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

        University university = getUniversity(universityId);
        UniversityTag universityTag = universityTagService.getUniversityTag(tagId);

        university.getUniversityTags().add(universityTag);
        return universityRepository.save(university);
    }

    // remove tag from university
    @Override
    @Transactional
    public University removeTag(Long universityId, Long tagId) {

        University university = getUniversity(universityId);
        UniversityTag universityTag = universityTagService.getUniversityTag(tagId);

        university.getUniversityTags().remove(universityTag);
        return universityRepository.save(university);
    }

    // UNIVERSITY FACULTIES SECTION
    @Override
    public List<Faculty> getUniversityFaculties(Long universityId) {

        return facultyService.getAllFaculties().stream()
                .filter(faculty -> Objects.equals(faculty.getUniversity().getId(), universityId))
                .collect(Collectors.toList());
    }

    @Override
    public List<EntityModel<Faculty>> getUniversityFacultyModels(Long universityId) {

        return getUniversityFaculties(universityId).stream()
                .map(facultyService::convertFacultyToModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public University createAndAssignFaculty(Long universityId, Faculty facultyToCreate) {

        University university = getUniversity(universityId);
        facultyToCreate.setUniversity(university);
        facultyService.saveFaculty(facultyToCreate);

        return university;
    }

    @Override
    public List<Program> getPrograms(Long universityId) {

        University university = getUniversity(universityId);
        return programService.getPrograms().stream()
                .filter(program -> university.getPrograms().contains(program)).collect(Collectors.toList());
    }

    @Override
    public List<EntityModel<Program>> getProgramModels(Long universityId) {

        return getPrograms(universityId).stream()
                .map(programService::convertProgramToModel)
                .collect(Collectors.toList());
    }

    @Override
    public University addProgram(Long universityId, Long programId) {

        University university = getUniversity(universityId);
        Program program = programService.getProgramById(programId);

        university.getPrograms().add(program);
        return universityRepository.save(university);
    }

    @Override
    public University removeProgram(Long universityId, Long programId) {

        University university = getUniversity(universityId);
        Program program = programService.getProgramById(programId);

        university.getPrograms().remove(program);
        return universityRepository.save(university);
    }
}
