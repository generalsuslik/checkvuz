package checkvuz.checkvuz.university.university.repository;

import checkvuz.checkvuz.university.university.entity.UniversityTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityTagRepository extends JpaRepository<UniversityTag, Long> {
}
