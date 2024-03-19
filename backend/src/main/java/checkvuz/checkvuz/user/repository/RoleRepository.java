package checkvuz.checkvuz.user.repository;

import checkvuz.checkvuz.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByTitle(String title);
}
