package checkvuz.checkvuz.university.program.exception;

public class ProgramTagNotFoundByIdException extends RuntimeException {

    public ProgramTagNotFoundByIdException(Long programTagId) {
        super("Could not find program tag with id=" + programTagId);
    }
}
