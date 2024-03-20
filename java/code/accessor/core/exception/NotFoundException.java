package code.accessor.core.exception;

public class NotFoundException extends  BaseException {

    private static final String INITIAL_MSG = "Not Found ";

    public NotFoundException() {
        super(INITIAL_MSG);
    }

    public NotFoundException(String msg) {
        super(INITIAL_MSG + msg);
    }
}
