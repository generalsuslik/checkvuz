package checkvuz.checkvuz.utils.image.exception;

public class ImageNotFoundByTitleException extends RuntimeException {
    public ImageNotFoundByTitleException(String title) {
        super("Could not find image with title=" + title);
    }
}
