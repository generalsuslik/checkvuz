package checkvuz.checkvuz.user.exception;

public class UserNotFoundByIdException extends RuntimeException {
    public UserNotFoundByIdException(Long userId) {
        super("Could not find user with id=" + userId);
    }
}
