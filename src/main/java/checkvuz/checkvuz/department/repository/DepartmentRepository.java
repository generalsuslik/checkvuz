package checkvuz.checkvuz.department.repository;

import checkvuz.checkvuz.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
