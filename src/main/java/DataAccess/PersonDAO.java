package DataAccess;

import Model.Person;
import Model.User;
import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Data access object for Service.Person Model
 */
public class PersonDAO {

    /**
     * SQL Database Connection
     */
    private final Connection connection;

    /**
     * Constructor to initialize connection
     *
     * @param connection SQL database connection
     */
    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a person into the database
     *
     * @param person the person to be inserted into the database
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "insert into Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender, FatherID, MotherID, SpouseID) values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, String.valueOf(person.getGender()));
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException();
        }
    }

    public void update(Person person) throws DataAccessException {
        String sql = "UPDATE Persons SET FatherID == ?, MotherID == ?, SpouseID == ? WHERE PersonID == ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getFatherID());
            stmt.setString(2, person.getMotherID());
            stmt.setString(3, person.getSpouseID());
            stmt.setString(4, person.getPersonID());

            stmt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException();
        }
    }

    /**
     * Finds person based off of personID
     *
     * @param personID the personID of the person being searched for
     * @return the Person object
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID == ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender").charAt(0), rs.getString("FatherID"),
                        rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
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

    public Person find(String personID, String username) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID == ? and AssociatedUsername == ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.setString(2, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender").charAt(0), rs.getString("FatherID"),
                        rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
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
     * Removes a person from the database
     *
     * @param person to be removed from the database
     */
    public void remove(Person person) throws DataAccessException {
        String sql = "DELETE FROM Persons WHERE PersonID == ? and AssociatedUsername == ? and FirstName == ? " +
                "and LastName == ? and Gender == ? and FatherID == ? and MotherID == ? and SpouseID == ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, String.valueOf(person.getGender()));
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("SQL Error thrown when trying to remove " + person.getPersonID() + " from database");
        }
    }

    public void remove(String username) throws DataAccessException {
        String sql = "DELETE FROM Persons WHERE AssociatedUsername == ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error thrown when trying to remove " + username + " from database");
        }
    }

    /**
     * Clears all people from the database
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);

        } catch (SQLException throwables) {
            throw new DataAccessException("SQL Error occurred while clearing Persons");
        }
    }

    /**
     * Given the input user, return a list of all people associated with this user
     *
     * @param userName the user name of the user
     * @return list of all Service.Person Model objects that are connected with the given user
     */
    public List<Person> findFromUser(String userName) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            List<Person> people = new ArrayList<>();
            while (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender").charAt(0), rs.getString("FatherID"),
                        rs.getString("MotherID"), rs.getString("SpouseID"));
                people.add(person);
            }

            if (people.size() > 0) {
                return people;
            }
            return null;

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
    }

}
