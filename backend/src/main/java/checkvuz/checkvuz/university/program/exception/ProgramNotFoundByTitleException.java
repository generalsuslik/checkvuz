package checkvuz.checkvuz.university.program.exception;

public class ProgramNotFoundByTitleException extends RuntimeException {

    public ProgramNotFoundByTitleException(String title) {
        super("Could not find program with title=" + title);
    }
}
