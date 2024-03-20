package code.accessor.core.exception;

public class PrivilegeException extends BaseException{
    private static final String INITIAL_MSG = "There is a problem with privileges. ";

    public PrivilegeException() {
        super(INITIAL_MSG);
    }

    public PrivilegeException(String msg) {
        super(INITIAL_MSG + msg);
    }
}
