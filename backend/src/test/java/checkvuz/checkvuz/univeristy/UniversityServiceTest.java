package checkvuz.checkvuz.univeristy;

import checkvuz.checkvuz.university.university.entity.University;
import checkvuz.checkvuz.university.university.repository.UniversityRepository;
import checkvuz.checkvuz.university.university.service.UniversityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniversityServiceTest {

    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private UniversityService universityService;

    @Test
    public void UniversityService_CreateUniversity_ReturnsUniversity() throws IOException {

        // ARRANGE
        University universityToCreate = University.builder()
                .title("university_title")
                .slug("university-slug")
                .expandedTitle("expanded_title")
                .foundingYear(1900)
                .build();

        when(universityRepository.save(Mockito.any(University.class)))
                .thenReturn(universityToCreate);

        // ACT
        University savedUniversity = universityService.createUniversity(universityToCreate, null);

        // ASSERT
        Assertions.assertNotNull(savedUniversity);
    }

    @Test
    public void UniversityService_GetAllUniversities_ReturnsListOfUniversities() {

        // ARRANGE
        List<University> universities = new ArrayList<>();
        when(universityRepository.findAll()).thenReturn(universities);

        // ACT
        List<University> allUniversities = universityService.getUniversities();

        // ASSERT
        Assertions.assertNotNull(allUniversities);
    }

    @Test
    public void UniversityService_GetUniversityById_ReturnsUniversity() {

        // ARRANGE
        University university = University.builder()
                .id(1L)
                .title("university_title")
                .slug("university-slug")
                .expandedTitle("expanded_title")
                .foundingYear(1900)
                .build();

        when(universityRepository.findById(1L)).thenReturn(Optional.ofNullable(university));

        // ACT
        University savedUniversity = universityService.getUniversity(1L);

        // ASSERT
        Assertions.assertNotNull(savedUniversity);
        Assertions.assertEquals(savedUniversity.getId(), 1L);
        Assertions.assertEquals(savedUniversity.getTitle(), "university_title");
        Assertions.assertEquals(savedUniversity.getSlug(), "university-slug");
        Assertions.assertEquals(savedUniversity.getExpandedTitle(), "expanded_title");
        Assertions.assertNull(savedUniversity.getDescription());
        Assertions.assertEquals(savedUniversity.getFoundingYear(), 1900);
        Assertions.assertNull(savedUniversity.getUniversityImage());
        Assertions.assertNull(savedUniversity.getUniversityTags());
    }

    @Test
    public void UniversityService_UpdateUniversityPatchVersion_ReturnsUpdatedUniversity() {

        // ARRANGE
        University university = University.builder()
                .id(1L)
                .title("university_title")
                .slug("university-slug")
                .expandedTitle("expanded_title")
                .foundingYear(1900)
                .build();

        when(universityRepository.save(Mockito.any(University.class))).thenReturn(university);
        when(universityRepository.findById(1L)).thenReturn(Optional.ofNullable(university));

        // ACT
        Map<String, Object> params = new HashMap<>();
        params.put("id", 2L);
        params.put("title", "university_title_2");
        params.put("foundingYear", 1830);
        params.put("description", "description");

        University updatedUniversity = universityService.updateUniversity(params, 1L);

        // ASSERT
        Assertions.assertNotNull(updatedUniversity);
        Assertions.assertEquals(updatedUniversity.getId(), 2L);
        Assertions.assertEquals(updatedUniversity.getTitle(), "university_title_2");
        Assertions.assertEquals(updatedUniversity.getSlug(), "university-slug");
        Assertions.assertEquals(updatedUniversity.getExpandedTitle(), "expanded_title");
        Assertions.assertEquals(updatedUniversity.getFoundingYear(), 1830);
        Assertions.assertEquals(updatedUniversity.getDescription(), "description");
        Assertions.assertNull(updatedUniversity.getUniversityImage());
        Assertions.assertNull(updatedUniversity.getUniversityTags());
    }
}
