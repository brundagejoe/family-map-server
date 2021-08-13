package requests;

/**
 * Class for the request info of the fill service
 */
public class FillRequest {

    /**
     * user name of the user
     */
    private String username;

    /**
     * number of generations to create for the user
     */
    private Integer generations;

    /**
     * Constructor that takes all parameters
     * @param username user name of the user
     * @param generations number of generations to create for the user
     */
    public FillRequest(String username, Integer generations) {
        this.username = username;
        this.generations = generations;
    }

    /**
     * Constructor that requires no parameters
     */
    public FillRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGenerations() {
        return generations;
    }

    public void setGenerations(Integer generations) {
        this.generations = generations;
    }
}
