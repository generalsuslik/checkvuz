package checkvuz.checkvuz.university.university.repository;

import checkvuz.checkvuz.university.university.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    Optional<University> findByTitle(String title);
}
