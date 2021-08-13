package UnitTests.Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Service.services.ClearService;
import requests.RegisterRequest;
import results.RegisterResult;
import Service.services.RegisterService;
import requests.SingleEventRequest;
import results.SingleEventResult;
import Service.services.SingleEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SingleEventServiceTest {
    SingleEventRequest request;
    SingleEventService service;
    SingleEventResult result;

    String eventID;
    String authtoken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        new ClearService().clear();
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
        eventID = new EventDao(connection).findFromUser(rRequest.getUsername()).get(0).getEventID();
        authtoken = new AuthTokenDAO(connection).find(rRequest.getUsername()).getAuthtoken();

        db.closeConnection(false);

        request = new SingleEventRequest();
        service = new SingleEventService();
    }


    @Test
    public void testSuccessful() throws DataAccessException {
        request.setEventID(eventID);
        request.setAuthtoken(authtoken);

        result = service.event(request);

        assertEquals("user", result.getAssociatedUsername());
        assertTrue(result.getSuccess());

    }

    @Test
    public void testFailure() throws DataAccessException {
        request.setEventID("BADID");
        request.setAuthtoken(authtoken);

        result = service.event(request);

        assertNull(result.getCity());
        assertFalse(result.getSuccess());

        request.setEventID(eventID);
        request.setAuthtoken("BADAUTHTOKEN");

        result = service.event(request);

        assertNull(result.getCity());
        assertFalse(result.getSuccess());

    }
}
