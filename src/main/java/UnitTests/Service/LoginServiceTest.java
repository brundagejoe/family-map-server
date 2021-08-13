package UnitTests.Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import requests.LoginRequest;
import results.LoginResult;
import Service.services.LoginService;
import requests.RegisterRequest;
import Service.services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest extends ServiceTest{
    LoginRequest request;
    LoginService service;
    LoginResult result;

    @BeforeEach
    public void setUp() throws DataAccessException {
        RegisterService rService = new RegisterService();
        RegisterRequest rRequest = new RegisterRequest();
        rService = new RegisterService();
        rRequest = new RegisterRequest();
        rRequest.setUsername("user");
        rRequest.setPassword("password");
        rRequest.setEmail("email");
        rRequest.setFirstName("First");
        rRequest.setLastName("Last");
        rRequest.setGender('m');

        rService.register(rRequest);

        service = new LoginService();
        request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("password");
    }


    @Test
    public void testSuccessful() throws DataAccessException {
        result = service.login(request);
        assertEquals(request.getUsername(), result.getUsername());
        assertTrue(result.getSuccess());

        Database db = new Database();
        Connection conn = db.getConnection();
        AuthTokenDAO dao = new AuthTokenDAO(conn);
        assertNotNull(dao.find(request.getUsername()));
        assertEquals(dao.findFromAuthToken(result.getAuthtoken()).getAuthtoken(), result.getAuthtoken());
        db.closeConnection(false);
    }

    @Test
    public void testFailure() throws DataAccessException {
        request.setUsername("badUsername");
        result = service.login(request);

        assertFalse(result.getSuccess());
    }
}
