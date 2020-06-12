package exception;

public class MatrixException extends Throwable {
    public MatrixException(String message, int size_A, int size_B) {
        super(message);
    }
}
