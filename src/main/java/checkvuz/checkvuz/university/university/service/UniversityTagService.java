package checkvuz.checkvuz.university.university.service;

import checkvuz.checkvuz.university.university.assembler.UniversityTagModelAssembler;
import checkvuz.checkvuz.university.university.entity.UniversityTag;
import checkvuz.checkvuz.university.university.exception.UniversityTagNotFoundException;
import checkvuz.checkvuz.university.university.repository.UniversityTagRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UniversityTagService implements UniversityTagServiceInterface{

    private final UniversityTagModelAssembler universityTagModelAssembler;
    private final UniversityTagRepository universityTagRepository;

    @Override
    public List<UniversityTag> getUniversityTags() {

        return new ArrayList<>(universityTagRepository.findAll());
    }

    @Override
    @Transactional
    public UniversityTag createUniversityTag(UniversityTag universityTagToCreate) {

        return universityTagRepository.save(universityTagToCreate);
    }

    @Override
    public UniversityTag getUniversityTag(Long universityTagId) {

        return universityTagRepository
                .findById(universityTagId).orElseThrow(() -> new UniversityTagNotFoundException(universityTagId));
    }

    @Override
    @Transactional
    public UniversityTag updateUniversityTag(UniversityTag universityTagToUpdate, Long universityTagId) {
        return universityTagRepository.findById(universityTagId)
                .map(universityTag -> {
                    universityTag.setId(universityTagToUpdate.getId());
                    universityTag.setTitle(universityTagToUpdate.getTitle());
                    return universityTagRepository.save(universityTag);
                })
                .orElseGet(() -> {
                    universityTagToUpdate.setId(universityTagId);
                    return universityTagRepository.save(universityTagToUpdate);
                });
        }

    @Override
    public void deleteUniversityTag(Long universityTagId) {

        universityTagRepository.deleteById(universityTagId);
    }

    @Override
    public EntityModel<UniversityTag> convertUniversityTagToModel(UniversityTag universityTag) {
        return universityTagModelAssembler.toModel(universityTag);
    }
}
