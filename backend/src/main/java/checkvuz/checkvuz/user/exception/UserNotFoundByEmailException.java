package checkvuz.checkvuz.user.exception;

public class UserNotFoundByEmailException extends RuntimeException {
    public UserNotFoundByEmailException(String userEmail) {
        super("Could not find user with email=" + userEmail);
    }
}
