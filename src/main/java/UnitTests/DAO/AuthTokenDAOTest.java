package UnitTests.DAO;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthTokenDAOTest {
    private Database db;
    private AuthToken bestAuthToken;
    private AuthTokenDAO aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestAuthToken = new AuthToken("user", "a123");
        Connection connection = db.getConnection();
        db.clearTables();
        aDao = new AuthTokenDAO(connection);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertSuccess() throws DataAccessException {
        aDao.insert(bestAuthToken);
        AuthToken testAuthToken = aDao.find(bestAuthToken.getUsername());
        assertEquals(bestAuthToken, testAuthToken);
    }

    @Test
    public void insertFail() throws DataAccessException {
        aDao.insert(bestAuthToken);
        assertThrows(DataAccessException.class, () -> aDao.insert(bestAuthToken));
    }

    @Test void findSuccess() throws DataAccessException {
        AuthToken auth1 = new AuthToken("u1", "a1");
        AuthToken auth2 = new AuthToken("u2", "a2");
        aDao.insert(auth1);
        aDao.insert(auth2);

        AuthToken testAuth = aDao.find(auth1.getUsername());
        assertNotNull(testAuth);
        assertEquals(auth1, testAuth);
    }

    @Test
    void findFail() throws DataAccessException {
        assertNull(aDao.find(bestAuthToken.getUsername()));
    }

    @Test//Tests both remove methods
    public void removeSuccess() throws DataAccessException {
        aDao.insert(bestAuthToken);
        aDao.remove(bestAuthToken);
        assertNull(aDao.find(bestAuthToken.getUsername()));

        aDao.insert(bestAuthToken);
        aDao.remove(bestAuthToken.getUsername());
        assertNull(aDao.find(bestAuthToken.getUsername()));

    }

    @Test//Tests both remove methods
    public void removeAlternative() throws DataAccessException {
        //make sure removing an item not there does not crash
        assertNull(aDao.find(bestAuthToken.getUsername()));
        assertDoesNotThrow(() -> aDao.remove(bestAuthToken));
        assertDoesNotThrow(() -> aDao.remove(bestAuthToken.getUsername()));

    }

    @Test
    public void findFromAuthtoken() throws DataAccessException {
        aDao.insert(bestAuthToken);
        assertNotNull(aDao.findFromAuthToken(bestAuthToken.getAuthtoken()));
    }

    @Test
    public void findFromAuthtokenAlternative() throws DataAccessException {
        assertDoesNotThrow(() -> aDao.findFromAuthToken(bestAuthToken.getAuthtoken()));
    }

    @Test
    public void clearSuccess() throws DataAccessException {
        aDao.insert(bestAuthToken);
        aDao.clear();
        assertNull(aDao.find(bestAuthToken.getUsername()));
    }
}
