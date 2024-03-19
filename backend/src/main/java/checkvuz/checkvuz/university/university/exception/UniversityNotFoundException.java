package checkvuz.checkvuz.university.university.exception;

public class UniversityNotFoundException extends RuntimeException {
    public UniversityNotFoundException(Long universityId) {
        super("Could not find university with id=" + universityId);
    }
}
