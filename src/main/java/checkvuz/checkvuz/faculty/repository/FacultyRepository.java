package checkvuz.checkvuz.faculty.repository;

import checkvuz.checkvuz.faculty.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
