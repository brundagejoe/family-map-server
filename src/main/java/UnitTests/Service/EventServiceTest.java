package UnitTests.Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.Event;
import results.EventResult;
import Service.services.EventService;
import requests.RegisterRequest;
import results.RegisterResult;
import Service.services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest extends ServiceTest {
    EventService service;
    EventResult result;
    String authtoken;

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

        RegisterResult rResult = rService.register(rRequest);
        //eventID = rResult.get();
        Database db = new Database();
        Connection connection = db.getConnection();
        authtoken = new AuthTokenDAO(connection).find(rRequest.getUsername()).getAuthtoken();
        db.closeConnection(false);

        service = new EventService();

    }

    @Test
    public void testSuccess() throws DataAccessException {
        result = service.event(authtoken);

        List<Event> events = result.getData();

        assertTrue(events.size() > 0);
        assertTrue(result.getSuccess());

        assertEquals("user", events.get(0).getAssociatedUsername());
    }

    @Test
    public void testFailure() throws DataAccessException {
        result = service.event("BADAUTHTOKEN");

        assertNull(result.getData());
        assertFalse(result.getSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
