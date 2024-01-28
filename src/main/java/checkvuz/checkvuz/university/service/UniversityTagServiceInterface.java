package checkvuz.checkvuz.university.service;

import checkvuz.checkvuz.university.entity.UniversityTag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface UniversityTagServiceInterface {
    CollectionModel<EntityModel<UniversityTag>> getUniversityTags();

    ResponseEntity<?> createUniversityTag(UniversityTag universityTagToCreate);

    EntityModel<UniversityTag> getUniversityTag(Long universityTagId);

    ResponseEntity<?> updateUniversityTag(UniversityTag universityTagToUpdate, Long id);

    ResponseEntity<?> deleteUniversityTag(Long universityTagId);
}
