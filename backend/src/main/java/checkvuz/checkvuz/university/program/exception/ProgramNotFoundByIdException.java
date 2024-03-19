package checkvuz.checkvuz.university.program.exception;

public class ProgramNotFoundByIdException extends RuntimeException {

    public ProgramNotFoundByIdException(Long programId) {
        super("Could not find program with id=" + programId);
    }
}
