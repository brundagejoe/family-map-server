package DataAccess;

import Model.AuthToken;
import Model.Person;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Data access object for AuthToken Model
 */
public class AuthTokenDAO {

    private final Connection connection;

    public AuthTokenDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts the given Authtoken object into the database
     * @param authtoken authtoken to be inserted
     */
    public void insert(AuthToken authtoken) throws DataAccessException {
        String sql = "insert into Authorization (Username, AuthorizationToken) values (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getUsername());
            stmt.setString(2, authtoken.getAuthtoken());

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException();
        }
    }

    /**
     * Clears all authtoken rows from the database
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "DELETE FROM Authorization";
            stmt.executeUpdate(sql);

        } catch (SQLException throwables) {
            throw new DataAccessException("SQL Error occurred while clearing Events");
        }
    }

    /**
     * Removes given authtoken from the database
     * @param authToken Authtoken model object to be removed
     */
    public void remove(AuthToken authToken) throws DataAccessException {
        String sql = "DELETE FROM Authorization WHERE Username == ? and AuthorizationToken == ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authToken.getUsername());
            stmt.setString(2, authToken.getAuthtoken());

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("SQL Error thrown when trying to remove " + authToken.getAuthtoken() + " from database");
        }
    }

    public void remove(String username) throws DataAccessException {
        String sql = "DELETE FROM Authorization WHERE Username == ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error when trying to remove " + username + " from database");
        }
    }

    /**
     * Given the username, return the authtoken as a string
     * @param username username of the user
     * @return the authtoken associated with the given user
     */
    public AuthToken find(String username) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM Authorization WHERE Username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("Username"), rs.getString("AuthorizationToken"));
                return authToken;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error while finding authToken");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    public AuthToken findFromAuthToken(String authTokenString) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM Authorization WHERE AuthorizationToken = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authTokenString);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("Username"), rs.getString("AuthorizationToken"));
                return authToken;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error while finding authToken");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }
}
