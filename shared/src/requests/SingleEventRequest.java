package requests;

/**
 * Class for the request body of the /event/[eventID] service
 */
public class SingleEventRequest {

    /**
     * ID of the event
     */
    private String eventID;

    private String authtoken;

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
