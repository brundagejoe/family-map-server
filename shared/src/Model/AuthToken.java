package Model;

/**
 * Class for creating an authorization token to be associated with a username
 */
public class AuthToken {

    /**
     * The username of the user
     */
    private String username;

    /**
     * the unique authorization token connected to the user
     */
    private String authtoken;

    /**
     * Constructor for initialization that requires no parameters
     */
    public AuthToken(){}

    /**
     * Constructor that takes all of the parameters to create an auth token object
     * @param username the username of the user
     * @param authtoken the unique authorization token to be connected to the user
     */
    public AuthToken(String username, String authtoken) {
        this.username = username;
        this.authtoken = authtoken;
    }

    /**
     * Constructor that does not take the authtoken as a parameter
     * @param username the username of the user
     */
    public AuthToken(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof AuthToken)) {
            return false;
        }

        AuthToken inputToken = (AuthToken) obj;
        return inputToken.username.equals(this.username) &&
               inputToken.authtoken.equals(this.authtoken);
    }
}
