package UnitTests.Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.User;
import results.ClearResult;
import Service.services.ClearService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class ClearServiceTest extends ServiceTest{
    ClearService service;
    ClearResult result;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new ClearService();
    }


    @Test
    public void testSuccessful() throws DataAccessException {
        User bestUser = new User("u", "p", "e", "f", "l", 'm', "id1");
        result = service.clear();

        Database db = new Database();
        Connection conn = db.getConnection();


        UserDAO dao = new UserDAO(conn);
        assertNull(dao.find(bestUser.getUsername()));
        db.closeConnection(false);

        assertTrue(result.getSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("clear succeeded"));
    }

    @Test
    public void testAlternative() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);

        result = service.clear();

        //An empty database should still successfully clear
        assertTrue(result.getSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("clear succeeded"));
    }
}
