package checkvuz.checkvuz.university.faculty.exception;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(Long facultyId) {
        super("Could not find faculty with id=" + facultyId);
    }
}
