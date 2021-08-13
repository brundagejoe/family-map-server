package results;

/**
 * Abstract class of result, which holds values of a message and boolean for all other result classes to build off of
 */
public abstract class Result {
    /**
     * Message String to be outputted in http response
     *
     */
    private String message;
    /**
     * Boolean Identifier on whether or not the http request was successful
     */
    private Boolean success;

    /**
     * Constructor that has a parameter for every variable
     * @param message The input message for the error message string
     * @param success Whether or not the request was successful
     */
    public Result(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Constructor that requires no parameters
     */
    public Result(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
