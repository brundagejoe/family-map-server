package requests;

/**
 * Class for the request body of the login service
 */
public class LoginRequest {
    /**
     * user name of the user trying to log in
     */
    private String username;

    /**
     * password of the user trying to log in
     */
    private String password;

    /**
     * Constructor that require all parameters
     * @param username user name of the user
     * @param password password of the user
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor that requires no parameters
     */
    public LoginRequest() {}

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
}
