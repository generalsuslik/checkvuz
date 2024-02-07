package checkvuz.checkvuz.utils.image.exception;

public class ImageNotFoundByIdException extends RuntimeException {
    public ImageNotFoundByIdException(Long imageId) {
        super("Could not find image with id=" + imageId);
    }
}
