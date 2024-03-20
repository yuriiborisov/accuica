package code.accessor.core.exception;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class AccessDeniedException extends BaseException {
    public AccessDeniedException(String comment) {
        super("Operation is forbidden " + comment);
    }
    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(final String format, final Object... argArray) {
        super(format, argArray);
    }
}
