package requests;

/**
 * Class for the request body of the register service
 */
public class RegisterRequest {
    /**
     * username of the user
     */
    private String username;

    /**
     * password of the user
     */
    private String password;

    /**
     * email of the user
     */
    private String email;

    /**
     * first name of the user
     */
    private String firstName;

    /**
     * last name of the user
     */
    private String lastName;

    /**
     * character representation the gender of the user ('m' or 'f')
     */
    private Character gender;

    /**
     * Constructor that has all parameters available
     * @param username username of the user
     * @param password password of the user
     * @param email email of the user
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param gender character representation of the gender of the user ('m' or 'f')
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, Character gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Constructor without any parameters for initialization
     */
    public RegisterRequest(){}

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
}
