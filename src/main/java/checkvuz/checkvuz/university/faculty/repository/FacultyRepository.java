package checkvuz.checkvuz.university.faculty.repository;

import checkvuz.checkvuz.university.faculty.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
