package DataAccess;

import Model.Person;
import Model.User;
import org.sqlite.SQLiteException;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Data access object for User Model
 */
public class UserDAO {

    /**
     * SQL Connection to database
     */
    private final Connection connection;

    /**
     * Constructor to initialize connection
     * @param connection the SQL Connection to initialize
     */
    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a user into the database
     * @param user the user object to be inserted into the database
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT into Users (Username, Password, Email, FirstName, LastName, Gender, PersonID) " +
                "values (?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, String.valueOf(user.getGender()));
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();

        } catch (org.sqlite.SQLiteException e){
            throw new DataAccessException("Error inserting " + user.getUsername() + " into database (SQLiteException)");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error inserting " + user.getUsername() + " into the database (SQLException");
        }
    }

    /**
     * Given a username, the user is found in the database and a User object is returned
     * @param username the unique username of the user
     * @return a User object for the specified user.
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE Username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Gender").charAt(0),
                        rs.getString("PersonID"));
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error while finding event");
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

    public User find(String username, String password) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE Username = ? and Password = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Gender").charAt(0),
                        rs.getString("PersonID"));
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error while finding event");
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

    /**
     * Clears all people from the database
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("SQL Error occurred while clearing Users");
        }
    }

    /**
     * Removes the given user from the database
     * @param user the user to be removed
     */
    public void remove(User user) throws DataAccessException {
        String sql = "DELETE FROM Users WHERE Username == ? and Password == ? and Email == ? and FirstName == ? " +
                "and LastName == ? and Gender == ? and PersonID == ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, String.valueOf(user.getGender()));
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("SQL Error thrown when trying to remove " + user.getUsername() + " from database");
        }
    }

    public void remove(String username) throws DataAccessException {
        String sql = "DELETE FROM Users WHERE Username == ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("SQL Error thrown when trying to remove " + username + " from database");
        }
    }

}
