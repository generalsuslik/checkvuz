package checkvuz.checkvuz.user.exception;

public class RoleNotFoundByIdException extends RuntimeException {
    public RoleNotFoundByIdException(Integer roleId) {
        super("Could not find role with id=" + roleId);
    }
}
