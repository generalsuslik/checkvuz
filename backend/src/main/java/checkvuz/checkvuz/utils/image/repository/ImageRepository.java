package checkvuz.checkvuz.utils.image.repository;

import checkvuz.checkvuz.utils.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByTitle(String title);
}
