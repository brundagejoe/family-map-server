package UnitTests.Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import requests.FillRequest;
import results.FillResult;
import Service.services.FillService;
import requests.RegisterRequest;
import Service.services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest extends ServiceTest {

    FillRequest request;
    FillService service;
    FillResult result;

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

        request = new FillRequest();
        service = new FillService();
    }


    @Test
    public void testSuccessful() throws DataAccessException {
        request.setUsername("user");
        request.setGenerations(4);
        result = service.fill(request);

        assertTrue(result.getSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("successfully added"));
        assertTrue(result.getMessage().toLowerCase().contains("31 persons"));

        Database db = new Database();
        Connection conn = db.getConnection();
        assertNotNull(new PersonDAO(conn).findFromUser(request.getUsername()));
        db.closeConnection(false);

    }

    @Test
    public void testFailure() throws DataAccessException {
        //fill for a user that does not exist
        request.setUsername("badUser");
        result = service.fill(request);

        assertFalse(result.getSuccess());

    }
}
