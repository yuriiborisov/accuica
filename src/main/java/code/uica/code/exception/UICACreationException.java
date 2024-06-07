package code.uica.code.exception;

public class UICACreationException extends UICAException {
    private static final String INITIAL_MSG_PARAMETRIZED = "Cannot create UICA. UICA not found with Id: {}";
    public UICACreationException(String id) {
        super(INITIAL_MSG_PARAMETRIZED, id);
    }

}

