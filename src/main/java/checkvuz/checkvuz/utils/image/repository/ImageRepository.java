package checkvuz.checkvuz.utils.image.repository;

import checkvuz.checkvuz.utils.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
