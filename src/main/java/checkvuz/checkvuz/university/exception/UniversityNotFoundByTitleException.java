package checkvuz.checkvuz.university.exception;

public class UniversityNotFoundByTitleException extends RuntimeException {
    public UniversityNotFoundByTitleException(String title) {
        super("Could not find university with title=" + title);
    }
}
