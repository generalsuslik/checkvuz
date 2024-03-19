package checkvuz.checkvuz.university.university.service;

import checkvuz.checkvuz.university.university.entity.UniversityTag;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface UniversityTagServiceInterface {
    List<UniversityTag> getUniversityTags();

    UniversityTag createUniversityTag(UniversityTag universityTagToCreate);

    UniversityTag getUniversityTag(Long universityTagId);

    UniversityTag updateUniversityTag(UniversityTag universityTagToUpdate, Long id);

    void deleteUniversityTag(Long universityTagId);

    EntityModel<UniversityTag> convertUniversityTagToModel(UniversityTag universityTag);
}
