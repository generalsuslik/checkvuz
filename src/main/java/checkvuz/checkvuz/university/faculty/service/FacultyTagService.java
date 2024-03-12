package checkvuz.checkvuz.university.faculty.service;

import checkvuz.checkvuz.university.faculty.assembler.FacultyTagModelAssembler;
import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import checkvuz.checkvuz.university.faculty.exception.FacultyTagNotFoundException;
import checkvuz.checkvuz.university.faculty.repository.FacultyTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FacultyTagService implements FacultyTagServiceInterface {

    private final FacultyTagModelAssembler facultyTagModelAssembler;
    private final FacultyTagRepository facultyTagRepository;

    @Override
    public List<FacultyTag> getFacultyTags() {

        return new ArrayList<>(facultyTagRepository.findAll());
    }

    @Override
    public FacultyTag createFacultyTag(FacultyTag facultyTagToCreate) {

        return facultyTagRepository.save(facultyTagToCreate);
    }

    @Override
    public FacultyTag getFacultyTag(Long facultyTagId) {

        return facultyTagRepository
                .findById(facultyTagId).orElseThrow(() -> new FacultyTagNotFoundException(facultyTagId));
    }

    @Override
    public FacultyTag updateFacultyTag(FacultyTag facultyTagToUpdate, Long facultyTagId) {

        return facultyTagRepository.findById(facultyTagId)
                .map(facultyTag -> {
                    facultyTag.setId(facultyTagToUpdate.getId());
                    facultyTag.setTitle(facultyTagToUpdate.getTitle());
                    return facultyTagRepository.save(facultyTag);
                })
                .orElseGet(() -> {
                    facultyTagToUpdate.setId(facultyTagId);
                    return facultyTagRepository.save(facultyTagToUpdate);
                });
    }

    @Override
    public void deleteFacultyTag(Long facultyTagId) {

        facultyTagRepository.deleteById(facultyTagId);
    }

    @Override
    public EntityModel<FacultyTag> convertFacultyTagToModel(FacultyTag facultyTag) {
        return facultyTagModelAssembler.toModel(facultyTag);
    }
}
