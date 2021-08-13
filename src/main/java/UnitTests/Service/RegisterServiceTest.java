package UnitTests.Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import requests.RegisterRequest;
import results.RegisterResult;
import Service.services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest extends ServiceTest{
    RegisterRequest request;
    RegisterService service;
    RegisterResult result;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new RegisterService();
        request = new RegisterRequest();
        request.setUsername("user");
        request.setPassword("password");
        request.setEmail("email");
        request.setFirstName("First");
        request.setLastName("Last");
        request.setGender('m');
    }


    @Test
    public void testSuccessful() throws DataAccessException {
        result = service.register(request);
        assertEquals(request.getUsername(), result.getUsername());
        assertTrue(result.getSuccess());

        Database db = new Database();
        Connection conn = db.getConnection();
        AuthTokenDAO dao = new AuthTokenDAO(conn);
        assertNotNull(dao.find(request.getUsername()));
        assertEquals(dao.find(request.getUsername()).getAuthtoken(), result.getAuthtoken());
        db.closeConnection(false);
    }

    @Test
    public void testFailure() throws DataAccessException {
        service.register(request);

        //Register a user already registered
        result = service.register(request);

        assertFalse(result.getSuccess());
    }
}
