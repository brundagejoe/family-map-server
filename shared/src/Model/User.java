package Model;

/**
 * Class for users of the FamilyMap.
 * It is NOT for people to be represented in the family map, although every user must also be represented in the Service.Person class.
 */
public class User {
    /**
     * Unique username (non-empty string)
     */
    private String username;
    /**
     * User's password (non-empty string)
     */
    private String password;
    /**
     * User's email address (non-empty string)
     */
    private String email;
    /**
     * User's first name (non-empty string)
     */
    private String firstName;
    /**
     * User's last name (non-empty string)
     */
    private String lastName;
    /**
     * User’s gender (Character: “f” or “m”)
     */
    private Character gender;
    /**
     * Unique Service.Person ID assigned to this user’s generated Service.Person object (non-empty string)
     */
    private String personID;

    /**
     * Creates a user with all parameters available
     * @param username Unique username
     * @param password User's password
     * @param email User's email
     * @param firstName User's first name
     * @param lastName User's last name
     * @param gender User's gender (character: "f" or "m")
     * @param personID Unique Service.Person ID
     */
    public User(String username, String password, String email, String firstName, String lastName, Character gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    /**
     * Creates a user without requiring a personID
     * @param username Unique username
     * @param password User's password
     * @param email User's email
     * @param firstName User's first name
     * @param lastName User's last name
     * @param gender User's gender (character: "f" or "m")
     */
    public User(String username, String password, String email, String firstName, String lastName, Character gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Constructor for initialization that requires no parameters
     */
    public User(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        User comparedUser = (User)obj;

        return this.username.equals(comparedUser.username) &&
                this.password.equals(comparedUser.password) &&
                this.email.equals(comparedUser.email) &&
                this.firstName.equals(comparedUser.firstName) &&
                this.lastName.equals(comparedUser.lastName) &&
                this.gender.equals(comparedUser.gender) &&
                this.personID.equals(comparedUser.personID);
    }
}
