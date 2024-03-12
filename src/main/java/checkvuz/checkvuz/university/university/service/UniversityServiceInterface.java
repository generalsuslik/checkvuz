package checkvuz.checkvuz.university.university.service;

import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.university.entity.University;
import checkvuz.checkvuz.university.university.entity.UniversityTag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UniversityServiceInterface {
    List<University> getUniversities();

    University createUniversity(University universityToCreate);

    University getUniversity(Long universityId);

    University updateUniversity(University universityToUpdate, Long id);

    void deleteUniversity(Long id);

    EntityModel<University> convertUniversityToModel(University university);

    List<UniversityTag> getAssignedTags(Long universityId);

    List<EntityModel<UniversityTag>> getAssignedEntityTags(Long universityId);

    University assignTag(Long universityId, Long tagId);

    University removeTag(Long universityId, Long tagId);

    CollectionModel<EntityModel<Faculty>> getUniversityFaculties(Long universityId);

    ResponseEntity<EntityModel<Faculty>>  createAndAssignFaculty(Long universityId, Faculty facultyToCreate);
}
