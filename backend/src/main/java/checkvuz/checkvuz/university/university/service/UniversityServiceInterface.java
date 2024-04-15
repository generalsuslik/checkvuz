package checkvuz.checkvuz.university.university.service;

import checkvuz.checkvuz.university.faculty.entity.Faculty;
import checkvuz.checkvuz.university.program.entity.Program;
import checkvuz.checkvuz.university.university.entity.University;
import checkvuz.checkvuz.university.university.entity.UniversityTag;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UniversityServiceInterface {
    List<University> getUniversities();

    University createUniversity(University universityToCreate, MultipartFile imageFile) throws IOException;

    University getUniversity(Long universityId);

    University updateUniversity(University universityToUpdate, Long universityId);

    University updateUniversity(Map<String, Object> params, Long universityId);

    University addUniversityImage(Long universityId, MultipartFile imageToAdd) throws IOException;

    void deleteUniversity(Long id);

    EntityModel<University> convertUniversityToModel(University university);

    List<UniversityTag> getAssignedTags(Long universityId);

    List<EntityModel<UniversityTag>> getAssignedEntityTags(Long universityId);

    University assignTag(Long universityId, Long tagId);

    University removeTag(Long universityId, Long tagId);

    List<Faculty> getUniversityFaculties(Long universityId);

    List<EntityModel<Faculty>> getUniversityFacultyModels(Long universityId);

    University createAndAssignFaculty(Long universityId, Faculty facultyToCreate);

    List<Program> getPrograms(Long universityId);

    List<EntityModel<Program>> getProgramModels(Long universityId);

    University addProgram(Long universityId, Long programId);

    University removeProgram(Long universityId, Long programId);
}
