package checkvuz.checkvuz.university.department.repository;

import checkvuz.checkvuz.university.department.entity.DepartmentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentTagRepository extends JpaRepository<DepartmentTag, Long> {
}
