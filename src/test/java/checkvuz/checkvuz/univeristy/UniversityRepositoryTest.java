package checkvuz.checkvuz.univeristy;

import checkvuz.checkvuz.university.entity.University;
import checkvuz.checkvuz.university.exception.UniversityNotFoundByTitleException;
import checkvuz.checkvuz.university.exception.UniversityNotFoundException;
import checkvuz.checkvuz.university.repository.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UniversityRepositoryTest {

    @Autowired
    private UniversityRepository universityRepository;

    @Test
    public void universityRepository_saveUniversity_returnSavedUniversity() {

        // Arrange
        University university = University.builder()
                .title("university1")
                .expandedTitle("expanded_title1")
                .description("description")
                .foundingYear(1900)
                .build();

        // Act
        University savedUniversity = universityRepository.save(university);

        // Assert
        assertThat(savedUniversity).isNotNull();
        assertThat(savedUniversity.getId()).isGreaterThan(0);
        assertThat(savedUniversity.getTitle()).isEqualTo("university1");
        assertThat(savedUniversity.getExpandedTitle()).isEqualTo("expanded_title1");
        assertThat(savedUniversity.getDescription()).isEqualTo("description");
        assertThat(savedUniversity.getFoundingYear()).isEqualTo(1900);
    }

    @Test
    public void universityRepository_getAll_returnMoreThanOneUniversity() {

        University university1 = University.builder()
                .title("university1")
                .expandedTitle("expanded_title1")
                .description("description1")
                .foundingYear(1900)
                .build();

        University university2 = University.builder()
                .title("university2")
                .expandedTitle("expanded_title2")
                .description("description2")
                .foundingYear(1951)
                .build();

        universityRepository.save(university1);
        universityRepository.save(university2);

        List<University> universityList = universityRepository.findAll();

        assertThat(universityList).isNotNull();
        assertThat(universityList.size()).isEqualTo(2);
    }

    @Test
    public void universityRepository_findById_returnUniversity() {

        University university = University.builder()
                .title("university1")
                .expandedTitle("expanded_title1")
                .description("description1")
                .foundingYear(1900)
                .build();

        universityRepository.save(university);

        University foundedUniversity = universityRepository
                .findById(university.getId()).orElseThrow(() -> new UniversityNotFoundException(university.getId()));

        assertThat(foundedUniversity).isNotNull();
    }

    @Test
    public void universityRepository_findByTitle_returnUniversityNotNull() {

        University university = University.builder()
                .title("university")
                .expandedTitle("expanded_title")
                .description("description")
                .foundingYear(1900)
                .build();

        universityRepository.save(university);

        University foundedUniversity = universityRepository
                .findByTitle(university.getTitle())
                .orElseThrow(() -> new UniversityNotFoundByTitleException(university.getTitle()));

        assertThat(foundedUniversity).isNotNull();
    }

    @Test
    public void universityRepository_updateUniversity_returnUniversityNotNull() {

        University university = University.builder()
                .title("university")
                .expandedTitle("expanded_title")
                .description("description")
                .foundingYear(1900)
                .build();

        universityRepository.save(university);

        University foundedUniversity = universityRepository
                .findById(university.getId()).orElseThrow(() -> new UniversityNotFoundException(university.getId()));

        foundedUniversity.setTitle("updatedTitle");
        foundedUniversity.setDescription("updatedDescription");
        foundedUniversity.setFoundingYear(1951);

        University updatedUniversity = universityRepository.save(foundedUniversity);

        assertThat(updatedUniversity.getId()).isEqualTo(university.getId());
        assertThat(updatedUniversity.getTitle()).isEqualTo("updatedTitle");
        assertThat(updatedUniversity.getExpandedTitle()).isEqualTo(university.getExpandedTitle());
        assertThat(updatedUniversity.getDescription()).isEqualTo("updatedDescription");
        assertThat(updatedUniversity.getFoundingYear()).isEqualTo(1951);
        assertThat(updatedUniversity.getUniversityTags()).isEqualTo(university.getUniversityTags());
    }

    @Test
    public void universityRepository_deleteById_returnUniversityIsEmpty() {

        University university = University.builder()
                .title("university1")
                .expandedTitle("expanded_title1")
                .description("description1")
                .foundingYear(1900)
                .build();

        universityRepository.save(university);

        universityRepository.deleteById(university.getId());
        Optional<University> deletedUniversity = universityRepository.findById(university.getId());

        assertThat(deletedUniversity).isEmpty();
    }
}
