package checkvuz.checkvuz.university.exception;

public class UniversityNotFoundException extends RuntimeException {
    public UniversityNotFoundException(Long universityId) {
        super("Could not find university with id=" + universityId);
    }
}
