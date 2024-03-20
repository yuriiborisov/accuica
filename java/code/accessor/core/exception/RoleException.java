package code.accessor.core.exception;

public class RoleException extends  BaseException {

    private static final String INITIAL_MSG = "There is a problem with Role. ";

    public RoleException() {
        super(INITIAL_MSG);
    }

    public RoleException(String msg) {
        super(INITIAL_MSG + msg);
    }
}
