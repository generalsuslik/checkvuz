package checkvuz.checkvuz.utils.erros;

public class FieldCheckingException extends RuntimeException{

    public FieldCheckingException(String fieldName, Class<?> fieldType) {
        super("Field " + fieldName + " with type: " + fieldType + " is not allowed here");
    }

    public FieldCheckingException(String fieldName) {
        super("Field " + fieldName + " is not allowed here");
    }
}
