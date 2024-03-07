package checkvuz.checkvuz.user.exception;

public class UserNotFoundByUsernameException extends RuntimeException {
    public UserNotFoundByUsernameException(String username) {
        super("Could not find user '" + username + "'");
    }
}
