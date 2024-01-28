package checkvuz.checkvuz.university.repository;

import checkvuz.checkvuz.university.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
