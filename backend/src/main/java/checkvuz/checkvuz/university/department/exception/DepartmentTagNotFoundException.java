package checkvuz.checkvuz.university.department.exception;

public class DepartmentTagNotFoundException extends RuntimeException {
    public DepartmentTagNotFoundException(Long departmentTagId) {
        super("Could not find department tag with id=" + departmentTagId);
    }
}
