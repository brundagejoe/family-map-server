package UnitTests.Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

public class ServiceTest {

    @BeforeEach
    public void clearTables() throws DataAccessException {
        Database db = new Database();
        Connection connection = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }
}
