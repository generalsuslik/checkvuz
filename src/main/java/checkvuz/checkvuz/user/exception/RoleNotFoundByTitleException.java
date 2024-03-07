package checkvuz.checkvuz.user.exception;

public class RoleNotFoundByTitleException extends RuntimeException {
    public RoleNotFoundByTitleException(String roleTitle) {
        super("Could not find role '" + roleTitle + "'");
    }
}
