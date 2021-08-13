package DataAccess;
import javax.xml.crypto.Data;
import java.sql.*;

public class Database {

    /**
     * SQL Database connection
     */
    private Connection connection;

    /**
     * Opens the connection to the database
     * @return the connection to the database
     * @throws DataAccessException Exception thrown if accessing database is unsuccesful
     */
    public Connection openConnection() throws DataAccessException{
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyMap.db";

            connection = DriverManager.getConnection(CONNECTION_URL);

            connection.setAutoCommit(false);

        } catch (SQLException throwables) {
            throw(new DataAccessException("Unable to open connection to database"));
        }
        return connection;
    }

    public Connection getConnection() throws DataAccessException {
        if (connection == null) {
            return openConnection();
        }
        else {
            return connection;
        }
    }

    /**
     * Closes the connection to the databsae
     * @param commit whether or not to commit the changes (Boolean)
     * @throws DataAccessException exception thrown in closing database is unsuccesful
     */
    public void closeConnection(Boolean commit) throws DataAccessException {
        try {
            if (commit) {
                connection.commit();
            }
            else {
                connection.rollback();
            }

            connection.close();
            connection = null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    /**
     * Clears table
     * @throws DataAccessException exception thrown if there is an error clearing table
     */
    public void clearTables() throws DataAccessException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM Events";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM Authorization";
            stmt.executeUpdate(sql);

        } catch (SQLException throwables) {
            throw new DataAccessException("SQL Error occurred while clearing tables");
        }
    }
}
