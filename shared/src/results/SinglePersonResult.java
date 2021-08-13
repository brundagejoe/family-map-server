package results;

/**
 * Class for the result body of the /person/[personID] service
 */
public class SinglePersonResult extends Result {

    /**
     * Name of the user account this belongs too
     */
    private String associatedUsername;

    /**
     * Service.Person's unique ID
     */
    private String personID;

    /**
     * Service.Person's first name
     */
    private String firstName;

    /**
     * Service.Person's last name
     */
    private String lastName;

    /**
     * Character representation of the person's gender ('m' or 'f')
     */
    private Character gender;

    /**
     * ID of the person's father (optional)
     */
    private String fatherID;

    /**
     * ID of the person's mother (optional
     */
    private String motherID;

    /**
     * ID of the person's spouse
     */
    private String spouseID;

    /**
     * Constructor that requires all parameters
     * @param associatedUsername username of the user
     * @param personID person's unique ID
     * @param firstName person's first name
     * @param lastName person's last name
     * @param gender character representation of the person's gender ('m' or 'f')
     * @param message error message if failed
     * @param success boolean identifier if failed
     * @param fatherID person's father's ID
     * @param motherID person's mother's ID
     * @param spouseID person's spouse's ID
     */
    public SinglePersonResult(String associatedUsername, String personID, String firstName, String lastName, Character gender, String message, Boolean success, String fatherID, String motherID, String spouseID) {
        super(message, success);
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Constructor that does not require family member ID's
     * @param associatedUsername username of the user
     * @param personID person's unique ID
     * @param firstName person's first name
     * @param lastName person's last name
     * @param gender character representation of the person's gender ('m' or 'f')
     * @param message error message if failed
     * @param success boolean identifier if failed
     */
    public SinglePersonResult(String associatedUsername, String personID, String firstName, String lastName, Character gender, String message, Boolean success) {
        super(message, success);
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Constructor that does not require family member ID's or success or message parameters
     * @param associatedUsername username of the user
     * @param personID person's unique ID
     * @param firstName person's first name
     * @param lastName person's last name
     * @param gender character representation of the person's gender ('m' or 'f')

     */
    public SinglePersonResult(String associatedUsername, String personID, String firstName, String lastName, Character gender) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Constructor that requires no parameters
     */
    public SinglePersonResult() {
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
