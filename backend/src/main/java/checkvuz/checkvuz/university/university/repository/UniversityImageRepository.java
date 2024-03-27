package checkvuz.checkvuz.university.university.repository;

import checkvuz.checkvuz.university.university.entity.UniversityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityImageRepository extends JpaRepository<UniversityImage, Long> {

    Optional<UniversityImage> findUniversityImageByTitle(String title);
}
