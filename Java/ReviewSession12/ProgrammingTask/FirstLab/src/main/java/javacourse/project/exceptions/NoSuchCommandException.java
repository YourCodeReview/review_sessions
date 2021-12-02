package javacourse.project.exceptions;
public class NoSuchCommandException extends RuntimeException{
    public NoSuchCommandException(String message) {
        super(message);
    }
}
