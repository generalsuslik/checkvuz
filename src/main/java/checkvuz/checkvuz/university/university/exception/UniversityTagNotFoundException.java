package checkvuz.checkvuz.university.university.exception;

public class UniversityTagNotFoundException extends RuntimeException{
    public UniversityTagNotFoundException(Long universityTagId) {
        super("Could not find university tag with id=" + universityTagId);
    }
}
