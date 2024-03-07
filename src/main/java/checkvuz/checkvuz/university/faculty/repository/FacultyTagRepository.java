package checkvuz.checkvuz.university.faculty.repository;

import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyTagRepository extends JpaRepository<FacultyTag, Long> {
}
