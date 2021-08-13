package results;

/**
 * Class for the result body of the load service
 */
public class LoadResult extends Result {
    /**
     * Constructor that requires all the parameters
     * @param message error message or “Successfully added X users, Y persons, and Z events to the database.”
     * @param success boolean identifier for whether or not the request was succesful
     */
    public LoadResult(String message, Boolean success) {
        super(message, success);
    }

    /**
     * Constructor that requires no parameters
     */
    public LoadResult() {
    }
}
