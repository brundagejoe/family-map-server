package requests;

/**
 * Class for the request body of the /person/[personID] service
 */
public class SinglePersonRequest {

    /**
     * ID of the person who's information is requested
     */
    private String personID;

    private String authtoken;

    /**
     * Constructor that requires parameter
     * @param personID the ID of the person
     */
    public SinglePersonRequest(String personID) {
        this.personID = personID;
    }

    /**
     * Constructor that requires no parameters
     */
    public SinglePersonRequest() {
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
