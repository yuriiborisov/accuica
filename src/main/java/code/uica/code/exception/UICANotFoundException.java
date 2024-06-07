package code.uica.code.exception;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class UICANotFoundException extends UICAException {
    private static final String INITIAL_MSG_PARAMETRIZED = "UICA not found with Id: {}";
    public UICANotFoundException(String id) {
        super(INITIAL_MSG_PARAMETRIZED, id);
    }

}

