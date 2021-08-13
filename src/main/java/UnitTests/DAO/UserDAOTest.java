package UnitTests.DAO;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private Database db;
    private User bestUser;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("u", "p", "e", "f", "l", 'm', "id1");
        Connection connection = db.getConnection();
        db.clearTables();
        uDao = new UserDAO(connection);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(true);
    }

    @Test
    public void insertSuccess() throws DataAccessException {
        uDao.insert(bestUser);
        User testUser = uDao.find(bestUser.getUsername());
        assertEquals(bestUser, testUser);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }

    @Test
    void findSuccess() throws DataAccessException {
        User user1 = new User("u1", "p1", "e1", "f1", "l1", 'm', "id1");
        User user2 = new User("u2", "p2", "e2", "f2", "l2", 'm', "id2");
        uDao.insert(user1);
        uDao.insert(user2);

        User testUser = uDao.find(user1.getUsername());
        assertNotNull(testUser);
        assertEquals(user1, testUser );

        testUser = uDao.find(user1.getUsername(), user1.getPassword());
        assertNotNull(testUser);
        assertEquals(user1, testUser );
    }

    @Test
    void findFail() throws DataAccessException {
        assertDoesNotThrow(() -> uDao.find(bestUser.getPersonID()));
        assertDoesNotThrow(() -> uDao.find(bestUser.getUsername(), bestUser.getPassword()));

        assertNull(uDao.find(bestUser.getPersonID()));
        assertNull(uDao.find(bestUser.getUsername(), bestUser.getPassword()));
    }

    @Test
    public void clearSuccess() throws DataAccessException {
        User user1 = new User("u1", "p1", "e1", "f1", "l1", 'm', "id1");
        User user2 = new User("u2", "p2", "e2", "f2", "l2", 'm', "id2");
        uDao.insert(user1);
        uDao.insert(user2);
        uDao.clear();
        assertNull(uDao.find(user1.getPersonID()));
        assertNull(uDao.find(user2.getPersonID()));
    }

    @Test
    public void clearAlternative() throws DataAccessException {
        assertDoesNotThrow(() -> uDao.clear());
    }

    @Test
    public void removeSuccess() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.remove(bestUser);
        assertNull(uDao.find(bestUser.getPersonID()));

        uDao.insert(bestUser);
        uDao.remove(bestUser.getUsername());
        assertNull(uDao.find(bestUser.getPersonID()));
    }

    @Test
    public void removeAlternative() throws DataAccessException {
        assertDoesNotThrow(() -> uDao.remove(bestUser));
        assertDoesNotThrow(() -> uDao.remove(bestUser.getUsername()));
    }
}
