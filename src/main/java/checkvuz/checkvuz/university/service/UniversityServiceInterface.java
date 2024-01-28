package checkvuz.checkvuz.university.service;

import checkvuz.checkvuz.university.entity.University;
import checkvuz.checkvuz.university.entity.UniversityTag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface UniversityServiceInterface {
    CollectionModel<EntityModel<University>> getUniversities();

    ResponseEntity<?> createUniversity(University universityToCreate);

    EntityModel<University> getUniversity(Long universityId);

    ResponseEntity<?> updateUniversity(University universityToUpdate, Long id);

    ResponseEntity<?> deleteUniversity(Long id);

    CollectionModel<EntityModel<UniversityTag>> getAssignedTags(Long universityId);

    ResponseEntity<?> assignTag(Long universityId, Long tagId);
}
