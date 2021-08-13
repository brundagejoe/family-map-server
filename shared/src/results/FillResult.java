package results;

/**
 * Class for the result body of the fill service
 */
public class FillResult extends Result {

    /**
     * Constructor that takes all parameters
     * @param message message (if the clear succeeded or error message)
     * @param success boolean identifier on if operation was successful
     */
    public FillResult(String message, Boolean success) {
        super(message, success);
    }

    /**
     * Constructor that takes no parameters
     */
    public FillResult() {
    }
}
