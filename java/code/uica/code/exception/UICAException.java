package code.uica.code.exception;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class UICAException extends RuntimeException {
    private static final String INITIAL_MSG = "There is a problem with UICA. ";

    public UICAException() {
        super(INITIAL_MSG);
    }

    public UICAException(String msg) {
        super(INITIAL_MSG + msg);
    }


    protected UICAException(String format, Object... argArray) {
        super(getTuple(format, argArray).getMessage(), getTuple(format, argArray).getThrowable());
    }

    private static FormattingTuple getTuple(String format, Object[] argArray){
        return MessageFormatter.arrayFormat(format, argArray);
    }
}

