package results;

/**
 * Class for the result body of the /event/[eventID] service
 */
public class SingleEventResult extends Result {
    /**
     * associated username of event
     */
    private String associatedUsername;

    /**
     * event ID
     */
    private String eventID;

    /**
     * Person ID associated with event
     */
    private String personID;

    /**
     * Latitude of event
     */
    private Float latitude;

    /**
     * Longitude of event
     */
    private Float longitude;

    /**
     * Country event took place in
     */
    private String country;

    /**
     * City event took place in
     */
    private String city;

    /**
     * Type of event (birth, baptism, marriage, death)
     */
    private String eventType;

    /**
     * Year the event took place in
     */
    private Integer year;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
