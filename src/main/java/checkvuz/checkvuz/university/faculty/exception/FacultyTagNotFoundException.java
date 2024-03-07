package checkvuz.checkvuz.university.faculty.exception;

public class FacultyTagNotFoundException extends RuntimeException {
    public FacultyTagNotFoundException(Long facultyTagId) {
        super("Could not find faculty tag with id=" + facultyTagId);
    }
}
