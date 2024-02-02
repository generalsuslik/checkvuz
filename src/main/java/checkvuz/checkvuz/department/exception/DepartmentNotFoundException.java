package checkvuz.checkvuz.department.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long departmentId) {
        super("Could not find department with id=" + departmentId);
    }
}
