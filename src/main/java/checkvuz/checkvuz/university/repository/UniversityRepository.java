package checkvuz.checkvuz.university.repository;

import checkvuz.checkvuz.university.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {
    Optional<University> findByTitle(String title);
}
