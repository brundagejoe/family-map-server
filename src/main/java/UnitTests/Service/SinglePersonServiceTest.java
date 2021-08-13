package UnitTests.Service;

import DataAccess.DataAccessException;
import requests.RegisterRequest;
import results.RegisterResult;
import Service.services.RegisterService;
import requests.SinglePersonRequest;
import results.SinglePersonResult;
import Service.services.SinglePersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SinglePersonServiceTest extends ServiceTest {
    SinglePersonRequest request;
    SinglePersonService service;
    SinglePersonResult result;

    String personID;
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
        personID = rResult.getPersonID();
        authtoken = rResult.getAuthtoken();

        request = new SinglePersonRequest();
        service = new SinglePersonService();
    }


    @Test
    public void testSuccessful() throws DataAccessException {
        request.setPersonID(personID);
        request.setAuthtoken(authtoken);

        result = service.person(request);

        assertEquals("First", result.getFirstName());
        assertTrue(result.getSuccess());

    }

    @Test
    public void testFailure() throws DataAccessException {
        request.setPersonID("BADID");
        request.setAuthtoken(authtoken);

        result = service.person(request);

        assertNull(result.getFirstName());
        assertFalse(result.getSuccess());

        request.setPersonID(personID);
        request.setAuthtoken("BADAUTHTOKEN");

        result = service.person(request);

        assertNull(result.getFirstName());
        assertFalse(result.getSuccess());

    }
}
