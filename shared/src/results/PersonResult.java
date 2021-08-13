package results;

import Model.Person;

import java.util.List;

/**
 * Class for the result body of the person service
 */
public class PersonResult extends Result {
    /**
     * Array of person objects
     */
    private List<Person> data;

    /**
     * Constructor that has a parameter for every variable
     *
     * @param message The input message for the error message string
     * @param success Whether or not the request was successful
     * @param data List of person objects to return
     */
    public PersonResult(String message, Boolean success, List<Person> data) {
        super(message, success);
        this.data = data;
    }

    /**
     * Constructor that requires no parameters
     */
    public PersonResult() {
    }

    public List<Person> getData() {
        return data;
    }

    public void setData(List<Person> data) {
        this.data = data;
    }
}
